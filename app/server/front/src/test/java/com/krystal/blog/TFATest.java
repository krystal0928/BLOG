package com.krystal.blog;

import com.krystal.blog.common.util.TwoFactorAuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class TFATest {

    @Test
    public void testTFA() {
//        String tfaKey = TwoFactorAuthUtil.generateTFAKey();
        String tfaKey = "AZCGVPPQYETFAOMCMDSVMVRHKJESOHQV";
        String userName = System.currentTimeMillis() + "";
        log.info("tfaKey: {}", tfaKey);
        log.info(TwoFactorAuthUtil.generateOtpAuthUrl(userName, tfaKey));

        String code = "236036";
        boolean flag = TwoFactorAuthUtil.validateTFACode(tfaKey, code);
        Assertions.assertTrue(flag);
    }
}
