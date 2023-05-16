package dev.lotnest.adventure.common.message;

public class MessageSenders {

    public static final MessageSender COLORED = MessageSender.builder()
            .isPrefixed(false)
            .isColored(true)
            .isCentered(false)
            .build();
    public static final MessageSender CENTERED = MessageSender.builder()
            .isPrefixed(false)
            .isColored(false)
            .isCentered(true)
            .build();
    public static final MessageSender CENTERED_COLORED = MessageSender.builder()
            .isPrefixed(false)
            .isColored(true)
            .isCentered(true)
            .build();
    public static final MessageSender PREFIXED = MessageSender.builder()
            .isPrefixed(true)
            .isColored(false)
            .isCentered(false)
            .build();
    public static final MessageSender PREFIXED_COLORED = MessageSender.builder()
            .isPrefixed(true)
            .isColored(true)
            .isCentered(false)
            .build();
    public static final MessageSender PREFIXED_CENTERED = MessageSender.builder()
            .isPrefixed(true)
            .isColored(false)
            .isCentered(true)
            .build();
    public static final MessageSender PREFIXED_CENTERED_COLORED = MessageSender.builder()
            .isPrefixed(true)
            .isColored(true)
            .isCentered(true)
            .build();


    private MessageSenders() {
    }
}
