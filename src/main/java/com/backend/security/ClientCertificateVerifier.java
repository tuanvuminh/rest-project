package com.backend.security;

import com.backend.model.RESTResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.cert.X509Certificate;
import java.util.Date;

import static com.backend.consts.Constants.X509_CERTIFICATE;

@Default
@RequestScoped
public class ClientCertificateVerifier {

    private static final Logger LOG = LogManager.getLogger(ClientCertificateVerifier.class);

    public void filter(HttpServletRequest request) {

        X509Certificate cert = (X509Certificate) request.getAttribute(X509_CERTIFICATE);
        LOG.debug("Retrieved certificate: {}", cert);

        if (cert != null && validateCertificate(cert)) {
            LOG.debug("Certificate is valid.");
        } else {
            LOG.debug("Certificate is not valid.");
            throw new WebApplicationException("The provided certificate is not valid.", Response.Status.UNAUTHORIZED);
        }
    }

    private boolean validateCertificate(X509Certificate certificate) {
        try {
            Date currentDateTime = new Date();
            certificate.checkValidity(currentDateTime);

            String subject = certificate.getSubjectX500Principal().getName();
            return subject.equalsIgnoreCase("server");

        } catch (Exception e) {
            LOG.error("Certificate validation failed: {}", e.getMessage(), e);
            return false;
        }
    }
}
