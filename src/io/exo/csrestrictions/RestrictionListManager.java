package io.exo.csrestrictions;

import java.io.IOException;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Constructor;

import com.cloud.exception.InvalidParameterValueException;
import com.cloud.utils.PropertiesUtil;

public class RestrictionListManager {

    private static RestrictionList _restrictionList = null;
    private static Boolean _loaded = false;

    private static void _loadFromYaml(String data) {

        Constructor ctor = new Constructor(RestrictionList.class);
        TypeDescription tdesc = new TypeDescription(RestrictionList.class);

        tdesc.putMapPropertyType("restrictions", Restriction.class, Object.class);
        ctor.addTypeDescription(tdesc);
        Yaml parser = new Yaml(ctor);

        _restrictionList = (RestrictionList) parser.load(data);
    }

    private static void _loadRestrictionList() throws IOException {

        final Path path = PropertiesUtil.findConfigFile("restrictions.yaml").toPath();
        final byte[] ba = Files.readAllBytes(path);
        final String data = new String(ba, "UTF-8");

        _loadFromYaml(data);
        _loaded = true;
    }

    public static void enforceRestrictions(String serviceOfferingId, String templateName, Long templateSize) throws InvalidParameterValueException {

        try {
            final List<Restriction> restrictions = getRestrictions();

            for (Restriction restriction: restrictions) {

                if (restriction.getTemplateNamePattern() != null && templateName != null) {
                    if (serviceOfferingId.equals(restriction.getServiceOfferingId()) &&
                        restriction.getTemplateNamePattern().matcher(templateName).find()) {
                        throw new InvalidParameterValueException("Template is restricted for this service offering.");
                    }
                }

                if (restriction.getMaxTemplateSize() != null) {
                    if (serviceOfferingId.equals(restriction.getServiceOfferingId()) &&
                        (restriction.getMaxTemplateSize() < templateSize)) {
                        throw new InvalidParameterValueException("The required disk size is restricted for this template");
                    }
                }
            }

        } catch (IOException e) {
            /* we could not load restrictions, we should log but not interrupt execution */
        }

    }

    public static List<Restriction> getRestrictions() throws IOException {

        if (!_loaded) /* Only read once at startup */
            _loadRestrictionList();

        return _restrictionList.getRestrictions();
    }
}
