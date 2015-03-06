package io.exo.csrestrictions;

import java.util.regex.Pattern;

public class Restriction {
    private Long _maxTemplateSize = null;
    private String _templateName = null;
    private Pattern _templateNamePattern = null;
    private String _serviceOfferingName = null;

    public Long getMaxTemplateSize() {
        return _maxTemplateSize;
    }

    public String getTemplateName() {
        return _templateName;
    }

    public String getServiceOfferingName() {
        return _serviceOfferingName;
    }

    public Pattern getTemplateNamePattern() {
        if (_templateName != null && _templateNamePattern == null) {
            _templateNamePattern = Pattern.compile(_templateName);
        }
        return _templateNamePattern;
    }

    public void setMaxTemplateSize(Long maxTemplateSize) {
        _maxTemplateSize = maxTemplateSize;
    }

    public void setTemplateName(String templateName) {
        _templateName = templateName;
    }

    public void setServiceOfferingName(String serviceOfferingName) {
        _serviceOfferingName = serviceOfferingName;
    }
}
