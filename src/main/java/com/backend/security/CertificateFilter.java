package com.backend.security;

import com.backend.model.RESTResponse;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.security.cert.X509Certificate;
import java.util.Date;

import static com.backend.consts.Constants.X509_CERTIFICATE;

@Provider
public class CertificateFilter implements ContainerRequestFilter {

    private static final Logger LOG = LogManager.getLogger(CertificateFilter.class);

    @Override
    public void filter(ContainerRequestContext context) throws IOException {

        LOG.traceEntry();
        X509Certificate cert = (X509Certificate) context.getProperty(X509_CERTIFICATE);

        if (cert != null && validateCertificate(cert)) {
            LOG.debug("Certificate is valid.");
        } else {
            RESTResponse response = new RESTResponse();
            response.setMessageText("The provided certificate is not valid.");

            LOG.debug("Certificate is not valid.");
            context.abortWith(Response.status(Response.Status.FORBIDDEN).entity(response.toErrorResponse()).build());
        }
        LOG.traceExit();
    }

    private boolean validateCertificate(X509Certificate certificate) {
        try {
            Date currentDateTime = new Date();
            certificate.checkValidity(currentDateTime);

            String subject = certificate.getSubjectX500Principal().getName();
            return subject.equalsIgnoreCase("root");

        } catch (Exception e) {
            LOG.error("Certificate validation failed {}", e);
            return false;
        }
    }
}
