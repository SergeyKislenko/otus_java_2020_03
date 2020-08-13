package ru.otus;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.service.DBInitServise;
import ru.otus.core.service.DBInitServiseImpl;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.front.FrontendService;
import ru.otus.front.FrontendServiceImpl;
import ru.otus.front.handlers.GetUserDataRequestHandler;
import ru.otus.front.handlers.GetUserDataResponseHandler;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.messagesystem.*;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;

@EnableWebSocketMessageBroker
@Configuration
public class AppConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${name.frontendService}")
    private String FRONTEND_SERVICE_CLIENT_NAME;

    @Value("${name.databaseService}")
    private String DATABASE_SERVICE_CLIENT_NAME;

    @Bean(destroyMethod = "dispose")
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public MsClient databaseMsClient(DbServiceUserImpl dbServiceUser) {
        MessageSystem messageSystem = messageSystem();
        CallbackRegistry callbackRegistry = new CallbackRegistryImpl();
        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, requestHandlerDatabaseStore, callbackRegistry);
        requestHandlerDatabaseStore.addHandler(MessageType.CREATE_USER, getUserDataRequestHandler(dbServiceUser));
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

    @Bean
    public MsClient frontendMsClient() {
        MessageSystem messageSystem = messageSystem();
        CallbackRegistry callbackRegistry = new CallbackRegistryImpl();
        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, requestHandlerFrontendStore, callbackRegistry);
        requestHandlerFrontendStore.addHandler(MessageType.CREATE_USER, getUserDataResponseHandler(frontendMsClient));
        messageSystem.addClient(frontendMsClient);
        return frontendMsClient;
    }

    @Bean
    public RequestHandler getUserDataRequestHandler(DbServiceUserImpl dbServiceUser) {
        return new GetUserDataRequestHandler(dbServiceUser);
    }

    @Bean
    public RequestHandler getUserDataResponseHandler(MsClient frontendMsClient) {
        return new GetUserDataResponseHandler(frontendService(frontendMsClient));
    }

    @Bean
    public FrontendService frontendService(MsClient frontendMsClient) {
        return new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

    @Bean
    public <K, V> HwCache<K, V> cache() {
        return new MyCache<>();
    }

    @Bean
    public SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = HibernateUtils
                .buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
        return sessionFactory;
    }

    @Bean(initMethod = "initUserDb")
    public DBInitServise dbInitServise(DBServiceUser dbServiceUser) {
        return new DBInitServiseImpl(dbServiceUser);
    }
}
