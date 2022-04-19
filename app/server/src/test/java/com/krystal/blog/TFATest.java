package com.krystal.blog;

import com.krystal.blog.common.util.TwoFactorAuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

@Slf4j
public class TFATest {

    @Test
    public void testTFA() {
        String tfaKey = "BMQOORWXIJGIMHHWBUDRFYKVVJTTZYTD";
        String userName = System.currentTimeMillis() + "";
        log.info("tfaKey: {}", tfaKey);
        log.info(TwoFactorAuthUtil.generateOtpAuthUrl(userName, tfaKey));

        String code = "105354";
        boolean flag = TwoFactorAuthUtil.validateTFACode(tfaKey, code);
        Assertions.assertTrue(flag);
    }
}
