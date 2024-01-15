package com.backend.security;

import com.backend.exception.RESTException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.x500.X500Principal;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Objects;

import static com.backend.consts.Constants.X509_CERTIFICATE;

@Default
@Provider
@RequestScoped
public class CertificateValidator implements ContainerRequestFilter {

    private static final Logger LOG = LogManager.getLogger(CertificateValidator.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {

        try {
            X509Certificate[] certs = (X509Certificate[]) requestContext.getProperty(X509_CERTIFICATE);

            if (certs != null && validateCertificate(certs)) {
                LOG.debug("Certificate is valid.");
            } else {
                throw new RESTException("Certificate validation failed.");
            }

        } catch (RESTException | CertificateNotYetValidException | CertificateExpiredException e) {
            LOG.debug(e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build());
        }
    }

    private boolean validateCertificate(X509Certificate[] certificates)
            throws CertificateNotYetValidException, CertificateExpiredException {

        X509Certificate clientCertificate = certificates[0];
        LOG.debug("Retrieved certificate: {}", clientCertificate.getSubjectX500Principal());
        certificates[0].checkValidity(new Date());

        return Objects.requireNonNull(getCNValueFromX500Principal(clientCertificate.getSubjectX500Principal()))
                .equalsIgnoreCase("client");
    }

    private String getCNValueFromX500Principal(X500Principal x500Principal) {

        String x500Name = x500Principal.getName();
        String[] parts = x500Name.split(",");

        for (String part : parts) {
            if (part.trim().startsWith("CN=")) {
                return part.trim().substring(3);
            }
        }
        return null;
    }
}
