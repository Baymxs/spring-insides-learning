# Spring insides learning
Spring research project: BeanPostProcessor, ApplicationListener, BeanFactoryPostProcessor, Scope, ClassPathXmlApplicationContext, AnnotationApplicationContext.

### BeanPostProcessor
* позволяет настраивать бины еще до того, как они попадут в контекст;
* у этого интерфейса есть 2 метода:
    * ***Object postProcessBeforeInitialization(Object bean, String beanName);***
    * ***Object postProcessAfterInitialization(Object bean, String beanName);***
* между двумя этими методами вызывается ***init*** метод. Cуществует несколько способов задать ***init*** метод:
    * init-method;
    * afterProperties;
    * @PostConstruct;
### BeanFactoryPostProcessor
* позволяет настраивать bean definitions до создания бинов. Этот интерфейс имеет единственный метод:
    * ***Object postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);***
* этот метод запустится на этапе, когда другие бины еще не создан и есть только bean definitions и сам BeanFactory;

### Application Listener
Одним из ключевых элементов Spring является ***ApplicationContext***, который управляет жизненным циклом бинов. В процессе своей работы он вызывает целый ряд, так называемых, событий (ContextStoppedEvent, ContextStartedEvent и т.д.)
Обработка этих событий обеспечивается классами ***ApplicationEvent*** и интерфейсом ***ApplicationListener***. И когда бин иплементирует интерфейс ***ApplicationListener***, то каждый раз, когда вызывается то или иное событие, бин получает об этом информацию.

Существует целый ряд стандартных событий в Spring Framework:
* ***ContextStartedEvent***
    Это событие публикуется, когда ApplicationContext запущен через метод start() интерфейса ConfigurableApplicationContext. После получения этого события мы можем выполнить необходимые нам действия (например, записать то-то в базу данных и т.д.).

* ***ContextRefreshedEvent***
    Это событие публикуется когда ApplicationContext обновлён или инициализирован. Оно может быть вызвано использованием метода refresh() интерфейса ConfigurableApplicationContext.

* ***ContextStoppedEvent***
    Это событие публикуется, когда ApplicationContext остановлен методом stop() интерфейса ConfigurableApplicationContext. Мы также можем дать команду выполнить определённую работу после получения этого события.

* ***ContextClosedEvent***
    Публикуется, когда ApplicationContext закрыт методом close() интерфейса ConfigurableApplicationContext. Закрытие контекста – это конец файла. После этого он не может быть перезапущен или обновлен.

* ***RequestHandledEvent***
    Это специальное событие, которое информирует нас о том, что все бины HTTP-запроса были обслужены (ориентирован на веб).

Из любого события можно вытащить контекст
Обработка событий в Spring однопоточна, а значит, если событие опубликовано, то все процессы будут блокированы, пока все адресаты не получат сообщение. Другими словами, необходимо быть крайне внимательными при планировании использовать обработку событий в приложении.

### Различные способы конфигураци контекста 
* для конфигурации контекста при помощи XML используется ***ClassPathXmlApplicationContext***;
* чтобы сканировать пакеты на наличие аннотаций для создания бинов (@Component и др. аннотации с @Component) используется:
    * ***<context:component-scan base-package = "com..."/>*** или ***@ComponentScan("com")***
    * ***new AnnotationConfigApplicationContext("com");***
для этого используется ***ClassPathBeanDefinitionScanner***
        * не является ни BPP, ни BFPP;
        * является ***ResouceLoaderAware***;
        * создает bean definitions из всех классов, над которыми стоит аннотация @Component и др. аннотации с @Component.
* ***java config*** - был создан для того, чтобы была возможность добавлять кастомную логику для конфигурации бинов.
    * ***new AnnotationConfigApplicationContext(JavaConfig.class);***
    * java config парсируется ***AnnotatedBeanDefinitionReader*** - он является частью AnnotationConfigApplicationContext, ничего не имплементирует, а лишь только регистрирует java config;
    * Обрабатывает java config ***ConfigurationClassPostProcessor*** - особый BFPP. Он создает bean definitions по @Bean, а также относится к:
        * @Import;
        * @ImportResource;
        * @ComponentScan.
    




