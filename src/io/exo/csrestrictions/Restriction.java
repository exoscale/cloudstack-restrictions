package io.exo.csrestrictions;

import java.util.regex.Pattern;

public class Restriction {
    private Long _maxTemplateSize = null;
    private String _templateName = null;
    private Pattern _templateNamePattern = null;
    private String _serviceOfferingId = null;

    public Long getMaxTemplateSize() {
        return _maxTemplateSize;
    }

    public String getTemplateName() {
        return _templateName;
    }

    public String getServiceOfferingId() {
        return _serviceOfferingId;
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

    public void setServiceOfferingId(String serviceOfferingId) {
        _serviceOfferingId = serviceOfferingId;
    }
}
