package org.adorsys.cryptoutils.extendedstoreconnection.impls.pathencryption;

import org.adorsys.encobject.complextypes.BucketDirectory;
import org.adorsys.encobject.complextypes.BucketPath;
import org.adorsys.encobject.exceptions.PathDecryptionException;
import org.adorsys.encobject.types.BucketPathEncryptionPassword;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * Created by peter on 25.09.18.
 */
public class BucketPathEncryptionTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(BucketPathEncryption.class);

    @Test
    public void testContainerOnly() {
        BucketPath bucketPath = new BucketPath("peter");
        BucketPathEncryptionPassword bucketPathEncryptionPassword = new BucketPathEncryptionPassword("affeAFFE1!");
        doTest(bucketPath, bucketPathEncryptionPassword);
    }

    @Test
    public void testSimplePath() {
        BucketPath bucketPath = new BucketPath("peter/folder1");
        BucketPathEncryptionPassword bucketPathEncryptionPassword = new BucketPathEncryptionPassword("affeAFFE1!");
        doTest(bucketPath, bucketPathEncryptionPassword);
    }

    @Test
    public void testDeepPathPath() {
        BucketPath bucketPath = new BucketPath("peter/folder1/1/2/3");
        BucketPathEncryptionPassword bucketPathEncryptionPassword = new BucketPathEncryptionPassword("affeAFFE1!");
        doTest(bucketPath, bucketPathEncryptionPassword);
    }

    @Test(expected = PathDecryptionException.class)
    public void testWrongPassword() {
        BucketPath bucketPath = new BucketPath("peter/folder1/1/2/3");
        BucketPathEncryptionPassword bucketPathEncryptionPassword1 = new BucketPathEncryptionPassword("affeAFFE1!");
        BucketPathEncryptionPassword bucketPathEncryptionPassword2 = new BucketPathEncryptionPassword("affeAFFE2!");
        BucketPath encryptedBucketPath = BucketPathEncryption.encrypt(bucketPathEncryptionPassword1, bucketPath);
        BucketPath decryptedBucketPath = BucketPathEncryption.decrypt(bucketPathEncryptionPassword2, encryptedBucketPath);
    }

    @Test
    public void lasttest() {
        int NUMBER = 1000;
        BucketPath bucketPath = new BucketPath("peter/folder1/1/2/3");
        BucketPathEncryptionPassword bucketPathEncryptionPassword = new BucketPathEncryptionPassword("affeAFFE1!");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < NUMBER; i++) {
            BucketPath encryptedBucketPath = BucketPathEncryption.encrypt(bucketPathEncryptionPassword, bucketPath);
            BucketPath decryptedBucketPath = BucketPathEncryption.decrypt(bucketPathEncryptionPassword, encryptedBucketPath);
        }
        stopWatch.stop();
        LOGGER.info("time for " + NUMBER + " en- and decryptions took " + stopWatch.toString());

    }

    private void doTest(BucketPath bucketPath, BucketPathEncryptionPassword bucketPathEncryptionPassword) {
        BucketPath encryptedBucketPath = BucketPathEncryption.encrypt(bucketPathEncryptionPassword, bucketPath);
        BucketPath decryptedBucketPath = BucketPathEncryption.decrypt(bucketPathEncryptionPassword, encryptedBucketPath);
        LOGGER.debug("    plain bucket path:" + bucketPath);
        LOGGER.debug("encrypted bucket path:" + encryptedBucketPath);
        LOGGER.debug("decrypted bucket path:" + decryptedBucketPath);
        Assert.assertEquals(bucketPath, decryptedBucketPath);
        if (BucketPathEncryption.encryptContainer) {
            Assert.assertNotEquals(bucketPath.getObjectHandle().getContainer(), encryptedBucketPath.getObjectHandle().getContainer());
        } else {
            Assert.assertEquals(bucketPath.getObjectHandle().getContainer(), encryptedBucketPath.getObjectHandle().getContainer());
        }
    }

    @Test
    public void dtestContainerOnly() {
        BucketDirectory bucketDirectory = new BucketDirectory("peter");
        BucketPathEncryptionPassword bucketPathEncryptionPassword = new BucketPathEncryptionPassword("affeAFFE1!");
        doTest(bucketDirectory, bucketPathEncryptionPassword);
    }

    @Test
    public void dtestSimplePath() {
        BucketDirectory bucketDirectory = new BucketDirectory("peter/folder1");
        BucketPathEncryptionPassword bucketPathEncryptionPassword = new BucketPathEncryptionPassword("affeAFFE1!");
        doTest(bucketDirectory, bucketPathEncryptionPassword);
    }

    @Test
    public void dtestDeepPathPath() {
        BucketDirectory bucketDirectory = new BucketDirectory("peter/folder1/1/2/3");
        BucketPathEncryptionPassword bucketPathEncryptionPassword = new BucketPathEncryptionPassword("affeAFFE1!");
        doTest(bucketDirectory, bucketPathEncryptionPassword);
    }

    @Test(expected = PathDecryptionException.class)
    public void dtestWrongPassword() {
        BucketDirectory BucketDirectory = new BucketDirectory("peter/folder1/1/2/3");
        BucketPathEncryptionPassword bucketPathEncryptionPassword1 = new BucketPathEncryptionPassword("affeAFFE1!");
        BucketPathEncryptionPassword bucketPathEncryptionPassword2 = new BucketPathEncryptionPassword("affeAFFE2!");
        org.adorsys.encobject.complextypes.BucketDirectory encryptedBucketDirectory = BucketPathEncryption.encrypt(bucketPathEncryptionPassword1, BucketDirectory);
        org.adorsys.encobject.complextypes.BucketDirectory decryptedBucketDirectory = BucketPathEncryption.decrypt(bucketPathEncryptionPassword2, encryptedBucketDirectory);
    }

    private void doTest(BucketDirectory bucketDirectory, BucketPathEncryptionPassword bucketPathEncryptionPassword) {
        BucketDirectory encryptedBucketDirectory = BucketPathEncryption.encrypt(bucketPathEncryptionPassword, bucketDirectory);
        BucketDirectory decryptedBucketDirectory = BucketPathEncryption.decrypt(bucketPathEncryptionPassword, encryptedBucketDirectory);
        LOGGER.debug("    plain bucket path:" + bucketDirectory);
        LOGGER.debug("encrypted bucket path:" + encryptedBucketDirectory);
        LOGGER.debug("decrypted bucket path:" + decryptedBucketDirectory);
        Assert.assertEquals(bucketDirectory, decryptedBucketDirectory);
        if (BucketPathEncryption.encryptContainer) {
            Assert.assertNotEquals(bucketDirectory.getObjectHandle().getContainer(), encryptedBucketDirectory.getObjectHandle().getContainer());
        } else {
            Assert.assertEquals(bucketDirectory.getObjectHandle().getContainer(), encryptedBucketDirectory.getObjectHandle().getContainer());
        }
    }

    /**
     * Zeigt einfach nur, wie sich die Länge des Bucketpathnamen durch die Verschlüsselung vergrößert.
     */
    @Test
    public void showSizeIncreasing() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        Map<Integer, List<String>> passwordToRanges = new HashMap<>();
        String lang = "dasisteinsehrlangesverzeichnisundwennesverschluesseltwirddannistesnochviellaenger0973423984z7sdfkasdfasdfas1df43";
        String password = "1=Ss;;1=Ss;;1=Ss;;1=Ss;;1=Ss;;1=Ss;;1=Ss;;1=Ss;;1=Ss;;1=Ss;;1=Ss;;1=Ss;;";
        for (int p = 10; p < password.length(); p++) {
            BucketPathEncryptionPassword bucketPathEncryptionPassword = new BucketPathEncryptionPassword(password.substring(0, p));
            List<String> range = new ArrayList<>();
            passwordToRanges.put(p, range);
            int lastVorher = -1;
            int lastI = -1;
            int lastNachher = -1;
            int pwl = bucketPathEncryptionPassword.getValue().length();
            for (int i = 3; i < lang.length(); i++) {
                BucketDirectory bd = new BucketDirectory(lang.substring(0, i));
                BucketDirectory ebf = BucketPathEncryption.encrypt(bucketPathEncryptionPassword, bd);

                int vorher = bd.getObjectHandle().getContainer().length();
                int nachher = ebf.getObjectHandle().getContainer().length();

                if (lastVorher == -1) {
                    lastVorher = vorher;
                }
                if (lastNachher == -1) {
                    lastNachher = nachher;
                }
                if (nachher != lastNachher) {
                    String s = String.format("%3d-%3d -> %3d",
                            lastVorher,
                            lastI,
                            lastNachher);
                    range.add(s);
                    sb.append(s);
                    sb.append("\n");
                    lastVorher = vorher;
                    lastNachher = nachher;
                }
                lastI = i;
            }
            String s = String.format("%3d-%3d -> %3d",
                    lastVorher,
                    lastI,
                    lastNachher);
            range.add(s);
            sb.append(s);
            sb.append("\n");
        }
        List<String> ranges = null;
        int refkey = -1;
        int diffkey = -1;
        for (Integer key : passwordToRanges.keySet()) {
            if (ranges == null) {
                ranges = passwordToRanges.get(key);
                refkey = key;
            } else {
                List<String> nextRange = passwordToRanges.get(key);
                if (! ranges.equals(nextRange)) {
                    diffkey = key;
                }
            }
        };
        if (diffkey != -1) {
            LOGGER.info(sb.toString());
            LOGGER.info("passwordlegnth " + refkey + " differs from passwordlength " + diffkey);
        } else {
            LOGGER.info("independent of password size, the ranges are");
            ranges.forEach(range -> LOGGER.info(range));
        }
    }


}
