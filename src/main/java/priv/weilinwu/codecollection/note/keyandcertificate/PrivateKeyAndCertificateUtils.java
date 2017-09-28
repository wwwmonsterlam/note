package priv.weilinwu.codecollection.note.keyandcertificate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * dependency: org.slf4j/slf4j-api
 *             org.bouncycastle/bcprov-jdk15on
 *
 */
public class PrivateKeyAndCertificateUtils {

    public static final Logger logger = LoggerFactory.getLogger(PrivateKeyAndCertificateUtils.class);
    
    public KeyPair generateKeyPair() throws Exception {
        logger.info("Generate a key pair");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    public Certificate generateCertificate(Calendar notAfter, KeyPair keyPair, 
            String commonName, String organization, String organizationalUnit, String emailAddress) throws Exception {
        logger.info("Generate a certificate");

        // define a date range
        Calendar notBefore = new GregorianCalendar();
        notBefore.set(Calendar.YEAR, notBefore.get(Calendar.YEAR) - 1);

        // define a distinguished name
        List<Rdn> rdns = new ArrayList<>();
        rdns.add(new Rdn("CN", commonName));
        rdns.add(new Rdn("O", organization));
        rdns.add(new Rdn("OU", organizationalUnit));
        rdns.add(new Rdn("EmailAddress", emailAddress));
        LdapName ldapName = new LdapName(rdns);
        X509Principal subjectDN = new X509Principal(ldapName.toString());

        // define a certificate
        X509V3CertificateGenerator serverCertGen = new X509V3CertificateGenerator();
        serverCertGen.setSerialNumber(new BigInteger(32, new SecureRandom()));
        serverCertGen.setNotBefore(notBefore.getTime());
        serverCertGen.setNotAfter(notAfter.getTime());
        serverCertGen.setSubjectDN(subjectDN);
        serverCertGen.setIssuerDN(subjectDN);
        serverCertGen.setPublicKey(keyPair.getPublic());
        serverCertGen.setSignatureAlgorithm("SHA256WithRSA");
        serverCertGen.addExtension(X509Extensions.BasicConstraints, false, new BasicConstraints(true));

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // generate the certificate
        X509Certificate certificate = serverCertGen.generate(keyPair.getPrivate(), "BC");
        return certificate;
    }

    public void generatePKCS12(Certificate certificate, KeyPair keyPair, String path, String fileName,
            char[] passwd) throws Exception {
        logger.info("Generate PKCS12 file (.p12)");
        logger.info("The generated metadata will be saved in: {}", path);

        Certificate[] chain = new Certificate[1];
        chain[0] = certificate;
        KeyStore store = KeyStore.getInstance("PKCS12", "BC");
        store.load(null, null);
        store.setKeyEntry("Test Key", keyPair.getPrivate(), null, chain);
        FileOutputStream fOut = new FileOutputStream(path + fileName);
        store.store(fOut, passwd);
        fOut.close();
        logger.info("{} is generated", fileName);
    }    
    
    public Certificate getCertificateFromKeystore(String keystorePath, String keystorePass, String alias) throws Exception {
        logger.info("From [{}] get certificate with alias [{}]", keystorePath, alias);
        FileInputStream in = new FileInputStream(keystorePath);  
        KeyStore ks= KeyStore.getInstance("JKS"); 
        ks.load(in, keystorePass.toCharArray());  
        in.close();
        
        return ks.getCertificate(alias);
    }
    
    public String getCertificateStringFromKeystore(String keystorePath, String keystorePass, String alias) throws Exception {
        Certificate certificate = getCertificateFromKeystore(keystorePath, keystorePass, alias);
        
        return "-----BEGIN CERTIFICATE-----\n" + Base64.getEncoder().encodeToString(certificate.getEncoded()) + "\n-----END CERTIFICATE-----";
    }
    
    public PrivateKey getPrivateKeyFromKeystore(String keystorePath, String keystorePass, String alias, String privateKeyPass) throws Exception {
        logger.info("From [{}] get private key with alias [{}]", keystorePath, alias);
        FileInputStream in = new FileInputStream(keystorePath);  
        KeyStore ks= KeyStore.getInstance("JKS"); 
        ks.load(in, keystorePass.toCharArray());  
        in.close();
        
        return (PrivateKey) ks.getKey(alias, privateKeyPass.toCharArray());
    }
    
    public String getPrivateKeyStringFromKeystore(String keystorePath, String keystorePass, String alias, String privateKeyPass) throws Exception {
        PrivateKey pk = getPrivateKeyFromKeystore(keystorePath, keystorePass, alias, privateKeyPass);
        
        return "-----BEGIN PRIVATE KEY-----\n" + Base64.getEncoder().encodeToString(pk.getEncoded()) + "\n-----END PRIVATE KEY-----";
    }
}
