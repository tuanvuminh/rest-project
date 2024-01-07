package com.backend.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.cert.X509Certificate;

@Default
@RequestScoped
public class ClientCertificateVerifier {

    private static final Logger LOG = LogManager.getLogger(ClientCertificateVerifier.class);

    public void filter(ContainerRequestContext request) {

        X509Certificate[] clientCertificates = (X509Certificate[]) request.getProperty("jakarta.servlet.request.X509Certificate");

        X509Certificate clientCertificate = clientCertificates[0];

        String subjectDN = clientCertificate.getSubjectX500Principal().getName();
        String issuerDN = clientCertificate.getIssuerX500Principal().getName();

        System.out.println(subjectDN);
        System.out.println(issuerDN);
    }
}
