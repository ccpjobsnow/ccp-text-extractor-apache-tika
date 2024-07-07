package com.ccp.implementations.text.extractor.apache.tika;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Base64.Decoder;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import org.apache.tika.parser.Parser;
import org.xml.sax.ContentHandler;

import com.ccp.especifications.text.extractor.CcpTextExtractor;
import java.io.ByteArrayOutputStream;
import org.apache.tika.metadata.Metadata;

class ApacheTikaTextExtractor implements CcpTextExtractor {

	public String extractText(String content) {
		InputStream is = this.getParameterAsByteArrayInputStream(content);
		ParseContext context = new ParseContext();
		Detector detector = new DefaultDetector();
		Parser parser = new AutoDetectParser(detector);
		context.set(Parser.class, parser);
		OutputStream outputstream = new ByteArrayOutputStream();
		Metadata metadata = new Metadata();
		ContentHandler handler = new BodyContentHandler(outputstream);
		try {
			parser.parse(is, handler, metadata, context);
		} catch (IOException | SAXException | TikaException e) {
			throw new RuntimeException(e);
		}

		String extractedText = outputstream.toString();

		try {
			outputstream.close();
		} catch (IOException e) {
			// Silenciator tabajara
		}
		try {
			is.close();
		} catch (IOException e) {
			// Silenciator tabajara
		}
	
		return extractedText;
	}
	
	private ByteArrayInputStream getParameterAsByteArrayInputStream(String content) {

		byte[] byteArray = this.getByteArrayFromBase64String(content);

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);

		return byteArrayInputStream;

	}

	private byte[] getByteArrayFromBase64String(String content) {
		String[] split = content.split(",");
		String str = split[0];

		if (split.length > 1) {
			str = split[1];
		}

		String base64 = str;

		Decoder decoder = Base64.getDecoder();

		byte[] byteArray = decoder.decode(base64);
		return byteArray;
	}

}