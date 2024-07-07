package com.ccp.implementations.text.extractor.apache.tika;

import com.ccp.dependency.injection.CcpInstanceProvider;
import com.ccp.especifications.text.extractor.CcpTextExtractor;

public class CcpApacheTikaTextExtractor implements CcpInstanceProvider<CcpTextExtractor> {

	public CcpTextExtractor getInstance() {
		ApacheTikaTextExtractor textExtractor = new ApacheTikaTextExtractor();
		return textExtractor;
	}

}
