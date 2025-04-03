package com.bayudwiyansatria.spring.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for parsing Kubernetes error messages.
 * <p>
 * This class provides methods for extracting and parsing error messages returned from Kubernetes
 * API responses, cleaning the JSON of unwanted characters, and transforming them into
 * {@link JsonNode} objects that can be processed further.
 * </p>
 * <p>
 * The parsing and cleaning of the error message is especially useful for extracting error response
 * details in a structured manner.
 * </p>
 *
 * <p>
 * This class is final and cannot be instantiated, as it is designed to be used in a static
 * context.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
public final class KubernetesErrorParser {

    // Private constructor to prevent instantiation
    private KubernetesErrorParser() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Extracts and parses the response body from a Kubernetes error message.
     * <p>
     * This method scans the error message for the section that contains the HTTP response body,
     * extracts it, and parses it into a {@link JsonNode}. It then recursively cleans up the node by
     * replacing double quotes with single quotes within the JSON structure.
     * </p>
     *
     * @param errorMessage the Kubernetes error message string to parse
     * @return a {@link JsonNode} representing the parsed response body, or an empty object node if
     * parsing fails
     */
    public static JsonNode parseResponseBody(String errorMessage) {
        try {
            // Extract response body between HTTP response body: and HTTP response headers:
            int startIndex =
                errorMessage.indexOf("HTTP response body: ") + "HTTP response body: ".length();
            int endIndex = errorMessage.indexOf("HTTP response headers:");

            if (startIndex >= 0 && endIndex >= 0) {
                String jsonBody = errorMessage.substring(startIndex, endIndex).trim();
                log.debug("Extracted response body: {}", jsonBody);

                // Parse the JSON and clean quotes
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(jsonBody);
                return cleanJsonNodeQuotes(node);
            }

            return JsonNodeFactory.instance.objectNode();
        } catch (Exception e) {
            log.warn("Failed to parse Kubernetes error message: {}", errorMessage, e);
            return JsonNodeFactory.instance.objectNode();
        }
    }

    /**
     * Removes double quotes from message string.
     * <p>
     * This method is used to clean up strings by replacing all occurrences of double quotes with
     * single quotes, which is often required for displaying clean logs or responses.
     * </p>
     *
     * @param message the message string to clean
     * @return a cleaned string with single quotes replacing double quotes
     */
    private static String cleanQuotes(String message) {
        return message.replace("\"", "'");
    }

    /**
     * Recursively cleans quotes in the given {@link JsonNode}.
     * <p>
     * This method traverses the entire {@link JsonNode} structure and replaces double quotes with
     * single quotes in any textual fields. This is done recursively for objects and arrays as well,
     * ensuring that all nested elements are cleaned.
     * </p>
     *
     * @param node the {@link JsonNode} to recursively clean
     * @return a cleaned {@link JsonNode} with quotes replaced
     */
    private static JsonNode cleanJsonNodeQuotes(JsonNode node) {
        if (node.isObject()) {
            ObjectNode cleanedObject = JsonNodeFactory.instance.objectNode();
            node.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode value = cleanJsonNodeQuotes(entry.getValue());
                cleanedObject.set(key, value);
            });
            return cleanedObject;
        } else if (node.isArray()) {
            ArrayNode cleanedArray = JsonNodeFactory.instance.arrayNode();
            node.elements().forEachRemaining(element ->
                cleanedArray.add(cleanJsonNodeQuotes(element))
            );
            return cleanedArray;
        } else if (node.isTextual()) {
            return JsonNodeFactory.instance.textNode(
                cleanQuotes(node.asText())
            );
        }
        return node;
    }
}