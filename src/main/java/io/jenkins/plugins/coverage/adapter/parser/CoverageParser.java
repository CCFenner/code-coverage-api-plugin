package io.jenkins.plugins.coverage.adapter.parser;

import io.jenkins.plugins.coverage.targets.CoverageResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parse the standard format coverage report and convert it into {@link CoverageResult}
 */
public abstract class CoverageParser {

    /**
     * Parse coverage report to {@link CoverageResult}
     * @param document DOM document of coverage report
     * @return Coverage result
     */
    public CoverageResult parse(Document document) {
        CoverageResult result = processElement(document.getDocumentElement(), null);
        parse(document.getDocumentElement(), result);
        return result;
    }

    /**
     * Iterate the child elements of parent node, and parse the element to {@link CoverageResult}, then make the result
     * as the child of parent coverage result.
     * @param parent Parent Node
     * @param parentResult Parent coverage result
     */
    public void parse(Node parent, CoverageResult parentResult) {
        NodeList nodeList = parent.getChildNodes();
        for (int i = 0, len = nodeList.getLength(); i < len; i++) {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                CoverageResult r = processElement((Element) n, parentResult);
                parse(n, r);
            }
        }
    }

    /**
     * Process DOM element and convert to {@link CoverageResult}
     * @param current Current element
     * @param parentResult Parent coverage result
     * @return Coverage result
     */
    protected abstract CoverageResult processElement(Element current, CoverageResult parentResult);

}