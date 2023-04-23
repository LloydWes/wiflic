package fr.karmakat.wiflic.wsClient;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.tcp.TcpConnection;
import org.springframework.messaging.tcp.TcpConnectionHandler;
import org.springframework.util.Assert;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.handler.LoggingWebSocketHandlerDecorator;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.function.BiConsumer;

public class MyWebSocketClient {
    private MessageConverter messageConverter = new SimpleMessageConverter();

    private final WebSocketClient webSocketClient;
    public MyWebSocketClient(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }
    public void setMessageConverter(MessageConverter messageConverter) {
        Assert.notNull(messageConverter, "MessageConverter must not be null");
        this.messageConverter = messageConverter;
    }
    public CompletableFuture<StompSession> connectAsync(URI url, @Nullable WebSocketHttpHeaders handshakeHeaders, @Nullable String connectHeaders, StompSessionHandler sessionHandler) {
        Assert.notNull(url, "'url' must not be null");
        ConnectionHandlingStompSession session = this.createSession(connectHeaders, sessionHandler);
        WebSocketTcpConnectionHandlerAdapter adapter = new WebSocketTcpConnectionHandlerAdapter(session);
        this.getWebSocketClient().execute(new LoggingWebSocketHandlerDecorator(adapter), handshakeHeaders, url).whenComplete(adapter);
        return session.getSession();
    }

    protected ConnectionHandlingStompSession createSession(@Nullable String connectHeaders, StompSessionHandler handler) {
        DefaultStompSession session = new DefaultStompSession(handler, connectHeaders);
        session.setMessageConverter(this.getMessageConverter());
        session.setTaskScheduler(this.getTaskScheduler());
        session.setReceiptTimeLimit(this.getReceiptTimeLimit());
        return session;
    }

    private class WebSocketTcpConnectionHandlerAdapter implements BiConsumer<WebSocketSession, Throwable>, WebSocketHandler, TcpConnection<byte[]> {
        private final TcpConnectionHandler<byte[]> connectionHandler;
//        private final WebSocketStompClient.StompWebSocketMessageCodec codec = new WebSocketStompClient.StompWebSocketMessageCodec(WebSocketStompClient.this.getInboundMessageSizeLimit());
        @Nullable
        private volatile WebSocketSession session;
        private volatile long lastReadTime = -1L;
        private volatile long lastWriteTime = -1L;
        private final List<ScheduledFuture<?>> inactivityTasks = new ArrayList(2);

        public WebSocketTcpConnectionHandlerAdapter(TcpConnectionHandler<byte[]> connectionHandler) {
            Assert.notNull(connectionHandler, "TcpConnectionHandler must not be null");
            this.connectionHandler = connectionHandler;
        }

        public void accept(@Nullable WebSocketSession webSocketSession, @Nullable Throwable throwable) {
            if (throwable != null) {
                this.connectionHandler.afterConnectFailure(throwable);
            }

        }

        public void afterConnectionEstablished(WebSocketSession session) {
            this.session = session;
            this.connectionHandler.afterConnected(this);
        }

        public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) {
            this.lastReadTime = this.lastReadTime != -1L ? System.currentTimeMillis() : -1L;

            List messages;
            try {
//                messages = this.codec.decode(webSocketMessage);
                System.out.println("printing message" + webSocketMessage);
            } catch (Throwable var6) {
                this.connectionHandler.handleFailure(var6);
                return;
            }

//            Iterator var4 = messages.iterator();
//
//            while(var4.hasNext()) {
//                Message<byte[]> message = (Message)var4.next();
//                this.connectionHandler.handleMessage(message);
//            }

        }

        public void handleTransportError(WebSocketSession session, Throwable ex) throws Exception {
            this.connectionHandler.handleFailure(ex);
        }

        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            this.cancelInactivityTasks();
            this.connectionHandler.afterConnectionClosed();
        }

        private void cancelInactivityTasks() {
            Iterator var1 = this.inactivityTasks.iterator();

            while(var1.hasNext()) {
                ScheduledFuture<?> task = (ScheduledFuture)var1.next();

                try {
                    task.cancel(true);
                } catch (Throwable var4) {
                }
            }

            this.lastReadTime = -1L;
            this.lastWriteTime = -1L;
            this.inactivityTasks.clear();
        }

        public boolean supportsPartialMessages() {
            return false;
        }

        public CompletableFuture<Void> sendAsync(Message<byte[]> message) {
            this.updateLastWriteTime();
            CompletableFuture<Void> future = new CompletableFuture();

            try {
                WebSocketSession session = this.session;
                Assert.state(session != null, "No WebSocketSession available");
                session.sendMessage(this.codec.encode(message, session.getClass()));
                future.complete((Void) null);
            } catch (Throwable var7) {
                future.completeExceptionally(var7);
            } finally {
                this.updateLastWriteTime();
            }

            return future;
        }

        private void updateLastWriteTime() {
            long lastWriteTime = this.lastWriteTime;
            if (lastWriteTime != -1L) {
                this.lastWriteTime = System.currentTimeMillis();
            }

        }

        public void onReadInactivity(final Runnable runnable, final long duration) {
            Assert.state(WebSocketStompClient.this.getTaskScheduler() != null, "No TaskScheduler configured");
            this.lastReadTime = System.currentTimeMillis();
            Duration delay = Duration.ofMillis(duration / 2L);
            this.inactivityTasks.add(WebSocketStompClient.this.getTaskScheduler().scheduleWithFixedDelay(() -> {
                if (System.currentTimeMillis() - this.lastReadTime > duration) {
                    try {
                        runnable.run();
                    } catch (Throwable var5) {
                        if (WebSocketStompClient.logger.isDebugEnabled()) {
                            WebSocketStompClient.logger.debug("ReadInactivityTask failure", var5);
                        }
                    }
                }

            }, delay));
        }

        public void onWriteInactivity(final Runnable runnable, final long duration) {
            Assert.state(WebSocketStompClient.this.getTaskScheduler() != null, "No TaskScheduler configured");
            this.lastWriteTime = System.currentTimeMillis();
            Duration delay = Duration.ofMillis(duration / 2L);
            this.inactivityTasks.add(WebSocketStompClient.this.getTaskScheduler().scheduleWithFixedDelay(() -> {
                if (System.currentTimeMillis() - this.lastWriteTime > duration) {
                    try {
                        runnable.run();
                    } catch (Throwable var5) {
                        if (WebSocketStompClient.logger.isDebugEnabled()) {
                            WebSocketStompClient.logger.debug("WriteInactivityTask failure", var5);
                        }
                    }
                }

            }, delay));
        }

        public void close() {
            WebSocketSession session = this.session;
            if (session != null) {
                try {
                    session.close();
                } catch (IOException var3) {
                    if (WebSocketStompClient.logger.isDebugEnabled()) {
                        WebSocketStompClient.logger.debug("Failed to close session: " + session.getId(), var3);
                    }
                }
            }

        }
    }

}
