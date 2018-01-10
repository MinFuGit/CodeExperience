package com.fumin.study.demo.spring.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTemplate {
    private static final Pattern TEMPLATE_PATTERN = Pattern.compile("\\{\\s*(\\w+)\\s*\\}");

    public static String replace(final String template, final Map<String, String> params) {
        return replace(template, params, "", true);
    }

    public static String replace(final String template, final Map<String, String> params, final String defaultVal, boolean ignoreCase) {
        if (Objects.isNull(template) || Objects.isNull(params) || template.isEmpty() || params.isEmpty()) {
            return template;
        }
        Matcher matcher = TEMPLATE_PATTERN.matcher(template);

        Map<String, String> targetParmas = params;
        if (ignoreCase) {
            final Map<String, String> transferParam = new HashMap<>();
            params.entrySet().stream().forEach((entry) -> transferParam.put(entry.getKey().toUpperCase().trim(), entry.getValue()));
            targetParmas = transferParam;
        }

        final StringBuffer sb = new StringBuffer();
        final String dftVal = Optional.ofNullable(defaultVal).orElse("");
        while (matcher.find()) {
            String name = matcher.group(1);
            if (ignoreCase) {
                name = name.toUpperCase();
            }
            matcher.appendReplacement(sb, Optional.ofNullable(targetParmas.get(name)).orElse(dftVal));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
