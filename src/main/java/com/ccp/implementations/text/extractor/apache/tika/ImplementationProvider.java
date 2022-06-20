package com.ccp.implementations.text.extractor.apache.tika;

import com.ccp.dependency.injection.CcpEspecification.DefaultImplementationProvider;

public class ImplementationProvider extends DefaultImplementationProvider {

	@Override
	public Object getImplementation() {
		return new TextExtractorApacheTika();
	}

}
