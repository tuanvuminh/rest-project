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

import static com.backend.consts.Constants.X509CERTIFICATE;

@Provider
public class CertificateFilter implements ContainerRequestFilter {

    private static final Logger LOG = LogManager.getLogger(CertificateFilter.class);

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        LOG.traceEntry();

        X509Certificate[] certs = (X509Certificate[]) context.getProperty(X509CERTIFICATE);

        if (certs != null && validateCertificate(certs)) {
            LOG.debug("Certificate is valid.");
        } else {
            RESTResponse errorResponse = new RESTResponse();
            errorResponse.setMessageText("The provided certificate is not valid.");

            LOG.debug("Certificate is not valid.");
            context.abortWith(Response.status(Response.Status.FORBIDDEN).entity(errorResponse.toErrorResponse()).build());
        }
        LOG.traceExit();
    }

    private boolean validateCertificate(X509Certificate[] certs) {

        try {
            for (X509Certificate certificate : certs) {
                Date currentDateTime = new Date();
                certificate.checkValidity(currentDateTime);
            }
            return true;

        } catch (Exception e) {
            LOG.error("Certificate validation failed {}", e);
            return false;
        }
    }
}
