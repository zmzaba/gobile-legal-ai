package com.gobile.ai.service;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class DocumentService {
	
	public byte[] generateWordDocument(String content) {
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun run = title.createRun();
            run.setBold(true);
            run.setFontSize(14);
            run.setText("GOBILE & ASSOCIATES INCORPORATED");

            document.createParagraph();

            for (String line : content.split("\n")) {
                XWPFParagraph p = document.createParagraph();
                p.createRun().setText(line);
            }

            document.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Word document", e);
        }
    }
}
