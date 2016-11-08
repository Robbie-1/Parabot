package org.parabot.core.bdn.test;


import org.parabot.core.bdn.APIKey;
import org.parabot.core.bdn.APIKeyParticipant;

/**
 * @author EmmaStone
 */
public class Example {

    private static APIKey key;

    public static final APIKeyParticipant PARTICIPANT = new APIKeyParticipant() {
        @Override
        public void setAPIKey(APIKey key) {
            Example.key = key;
        }
    };
}
