package br.com.aguido.livautomation;

/**
 * Created by selem.gomes on 31/08/15.
 */
public class Constants {
    public static final int EXTRACT_MINIMIZED_TOP_ANIMATION_DURATION = 400;
    public static final int CPF_TEXT_SIZE_NOT_FORMATTED = 14;
    public static final int CPF_TEXT_SIZE = 11;
    public static final int PASSWORD_TEXT_SIZE = 6;

    public static final String KEY_SUMMARY_TYPE = "KEY_SUMMARY_TYPE";
    public static final int SUMMARY_ACCUMULATED = 1;
    public static final int SUMMARY_EXPIRE = 2;
    public static final int SUMMARY_RESCUED = 3;

    public static final boolean endpointAprovation = false;

    public static final String URL_FORGOT_PASSWORD = "https://m.empresa.com.br/mobile/secure/minha-conta/esqueci-senha";
    public static final String URL_REQUEST_CODE = "http://m.empresa.com.br/activate";
    public static final String URL_TERM = "http://m.empresa.com.br/mobile/livelo/livelo/o-programa/regulamento";

    private static final String URL_ACTIVATE_ACCOUNT_DEV = "http://m-dev.empresa.com.br/#mdlActivateAccount";
    private static final String URL_ACTIVATE_ACCOUNT_PROD = "http://m.empresa.com.br/#mdlActivateAccount";
    public static final String URL_ACTIVATE_ACCOUNT = endpointAprovation ?  URL_ACTIVATE_ACCOUNT_DEV : URL_ACTIVATE_ACCOUNT_PROD;

    public static final String URL_EDIT_ACCOUNT_PROD = "https://m.empresa.com.br/mobile/secure/myaccount/editProfile.jsp";

    public static final String GCM_SENDER_ID = "133121220836";

    public static class AccessConfiguration {
        public static String CLIENT_ID = "liveloapp";
        public static String SECRET = "Livelo15$";
    }

    public static final class SharedPrefsKeys {
        public static final String SHARED_PREF_NAME = "livelo_shared_pref";
        public static final String SHOW_TUTORIAL = "show_tutorial";
        public static final String LOGIN = "login_livelo";
        public static final String FINGERPRINT_ENABLE = "fingerprint_enable";
        public static final String FINGERPRINT_PASS = "fingerprint_pass";
        public static final String SESSION_ID_USER = "session_id_user";
        public static final String BRANDING_BANK = "branding_bank";


        public static final String SHARED_PREF_SUMMARY_NAME = "livelo_shared_pref_summary";
        public static final String KEY_SUMMARY = "key_summary";
        public static final String SHARED_PREF_EXTRACT_NAME = "livelo_shared_pref_extract";
        public static final String KEY_EXTRACT = "key_extract";

        public static final String SHARED_PREF_USER_PROFILE_NAME = "livelo_shared_pref_user_profile";
        public static final String KEY_USER_PROFILE = "key_user_profile";

        public static final String KEY_TIME = "key_time";

        public static final String DEVICE_ACTIVE = "device_active";
    }

    public static final class ApiErroCodeReturn {
        public static final int INVALID_TOKEN = 401;
    }

    public static final class WearParameters {
        public final static String REQUEST_CHECK_LOGIN = "checkLogin";
        public final static String REQUEST_GET_AMOUNT_POINTS = "getAmountPoints";
        public final static String REQUEST_VALIDATION_CODE = "validationCode";
        public final static String REQUEST_GO_TO_APP = "goToApp";
        public final static String REQUEST_PATH = "/request_livelo_path";
        public final static String RESPONSE_PATH = "/response_livelo_path";

        public final static String REQUEST_DELETE_SHARED = "/request_delete_shared";

        public final static String KEY_MAP_REQUEST_LOGIN = "wear_login";
        public final static String KEY_MAP_REQUEST_POINTS = "wear_points";
        public final static String KEY_MAP_REQUEST_VALIDATION_CODE = "wear_validation_code";
        public final static String KEY_MAP_REQUEST_ERROR = "wear_error";
    }

    public static final class JiraParser{
        public final static String JIRA_GET = "https://jira.empresa.com.br/rest/api/2/search?jql=reporter%20in(t27000023,gabriel.fraga,marllon.souza)&maxResults=5&startAt=2&project=Livelo&fields=key,summary,issuetype,created,priority,status,customfield_10015,components,resolution,reporter,assignee";
        public final static String JIRA_GET2 = "https://jira.empresa.com.br/rest/api/2/search?jql=reporter%20in(t27000023,gabriel.fraga,marllon.souza)&maxResults=5&startAt=2&project=Livelo";
        public final static String JIRA_GET3 = "https://192.168.0.1/rest/api/2/search";
        public final static String JIRA_GET4 = "https://jira.empresa.com.br/rest/api/2/search";

    }

    public static final class StatusOrder {
        public final static String ALL_STATUS = "ALL";
        public final static String PROCESSING_STATUS = "PROCESSING";
        public final static String PENDING_CUSTOMER_RETURN_STATUS = "PENDING_CUSTOMER_RETURN";
        public final static String PENDING_CUSTOMER_ACTION_STATUS = "PENDING_CUSTOMER_ACTION";
        public final static String PENDING_MERCHANT_ACTION_STATUS = "PENDING_MERCHANT_ACTION";
        public final static String SUBMITTED_STATUS = "SUBMITTED";
        public final static String NO_PENDING_ACTION_STATUS = "NO_PENDING_ACTION";
        public final static String PENDING_REMOVE_STATUS = "PENDING_REMOVE";
        public final static String REMOVED_STATUS = "REMOVED";
    }

    public static final class GoogleAnalytisEvents {
        public static final String KEY_GOOGLE_ANALYTICS = "UA-66602774-2";

        public static final String EVENT_SCREEN_LOGIN = "Login";
        public static final String EVENT_SCREEN_MY_POINTS = "Meus Pontos";
        public static final String EVENT_SCREEN_EXTRACT_HOSTORY = "Histórico do extrato";
        public static final String EVENT_SCREEN_POINTS_EXPIRE = "Pontos a expirar";
        public static final String EVENT_SCREEN_POINTS_ACCUMULATE = "Pontos acumulados";
        public static final String EVENT_SCREEN_POINTS_RESCUED = "Pontos resgatados";
        public static final String EVENT_SCREEN_ANY = "Qualquer tela";
        public static final String EVENT_SCREEN_MY_ACCOUNT = "Minha conta";
        public static final String EVENT_SCREEN_MY_DATA = "Meus dados";
        public static final String EVENT_SCREEN_HOME = "Home";
        public static final String EVENT_SCREEN_RESCUE_HISTORY = "Histórico de resgates";
        public static final String EVENT_SCREEN_PRODUCT_HISTORY = "Histórico do produto";
        public static final String EVENT_SCREEN_CHANGE_PASSWORD = "Alterar senha";
        public static final String EVENT_SCREEN_PRODUCT_CATALOG = "Catálogo de produtos";
        public static final String EVENT_SCREEN_CART = "Carrinho";
        public static final String EVENT_SCREEN_CHECKOUT_ADDRESS = "Endereço de entrega";

        public static final String EVENT_CATEGORY_LOGIN = "Login";
        public static final String EVENT_CATEGORY_MY_POINTS = "Meus Pontos";
        public static final String EVENT_CATEGORY_EXTRACT_HOSTORY = "Histórico do extrato";
        public static final String EVENT_CATEGORY_POINTS_EXPIRE = "Pontos a expirar";
        public static final String EVENT_CATEGORY_POINTS_ACCUMULATE = "Pontos acumulados";
        public static final String EVENT_CATEGORY_POINTS_RESCUED = "Pontos resgatados";
        public static final String EVENT_CATEGORY_MENU = "MENU";
        public static final String EVENT_CATEGORY_MY_ACCOUNT = "Minha conta";
        public static final String EVENT_CATEGORY_MY_DATA = "Meus dados";
        public static final String EVENT_CATEGORY_HOME = "Home";
        public static final String EVENT_CATEGORY_RESCUE_HISTORY = "Histórico de resgates";
        public static final String EVENT_CATEGORY_CHANGE_PASSWORD = "Alterar senha";
        public static final String EVENT_CATEGORY_PRODUCT_CATALOG = "Catálogo de produtos";
        public static final String EVENT_CATEGORY_CART = "Carrinho";
        public static final String EVENT_CATEGORY_CHECKOUT_ADDRESS = "Endereço de entrega";

        public static final String EVENT_ACTION_RESCUED_PASSWORD = "Recuperar senha";
        public static final String EVENT_ACTION_JOIN = "Entrar";
        public static final String EVENT_ACTION_JOIN_WITH_TOUCH_ID = "Touch ID";
        public static final String EVENT_ACTION_ACTIVATE_ACCOUNT = "Ativar conta";
        public static final String EVENT_ACTION_SUMMARY = "Resumo";
        public static final String EVENT_ACTION_POINTS_ACCUMULATED = "Pontos acumulados";
        public static final String EVENT_ACTION_POINTS_EXPIRE = "Pontos a expirar";
        public static final String EVENT_ACTION_POINTS_RESCUED = "Pontos resgatados";
        public static final String EVENT_ACTION_EXTRACT = "Extrato";
        public static final String EVENT_ACTION_HISTORY = "Histórico";
        public static final String EVENT_ACTION_ORDER_BY = "Organizar por";
        public static final String EVENT_ACTION_FILTER_BY = "Filtrar por";
        public static final String EVENT_ACTION_PARTNER_DETAIL = "Detalhamento do parceiro";
        public static final String EVENT_ACTION_FILTER = "Filtro";
        public static final String EVENT_ACTION_MONTH_DETAIL = "Detalhamento mensal";
        public static final String EVENT_ACTION_HOME = "Home";
        public static final String EVENT_ACTION_MY_ACCOUNT = "Minha conta";
        public static final String EVENT_ACTION_EXIT = "Sair";
        public static final String EVENT_ACTION_MY_POINTS = "Meus pontos";
        public static final String EVENT_ACTION_MY_DATA = "Meus dados";
        public static final String EVENT_ACTION_HISTORY_RESCUE = "Histórico de resgates";
        public static final String EVENT_ACTION_EDIT_DATA = "Editar dados";
        public static final String EVENT_ACTION_QR_CODE = "QR Code";
        public static final String EVENT_ACTION_GENERATE_CODE = "Gerador de código";
        public static final String EVENT_ACTION_RESCUE = "Resgate";
        public static final String EVENT_ACTION_PRODUCTS_CATALOG = "Catálogo de produtos";
        public static final String EVENT_ACTION_CONTACT_US = "Fale conosco";
        public static final String EVENT_ACTION_CHANGE_PASSWORD = "Alterar senha";
        public static final String EVENT_ACTION_FINGERPRINT = "Impressão digital";
        public static final String EVENT_ACTION_PASSWORD_CHANGED = "Impressão digital";
        public static final String EVENT_ACTION_SEARCH = "Impressão digital";
        public static final String EVENT_ACTION_CATEGORY = "Categoria";
        public static final String EVENT_ACTION_SUBCATEGORY = "Sub Categoria";
        public static final String EVENT_ACTION_PRODUCT = "Produto";
        public static final String EVENT_ACTION_PRODUCT_DESCRIPTION = "Descrição";
        public static final String EVENT_ACTION_ADD_PRODUCT = "Adicionar ao carrinho";
        public static final String EVENT_ACTION_CART_COUPON = "Cupom de desconto";
        public static final String EVENT_ACTION_CART_DELETE = "Excluir";
        public static final String EVENT_ACTION_CART_INCREASE_PRODUCT = "Adicionar unidade";
        public static final String EVENT_ACTION_CART_DECREASE_PRODUCT = "Retirar unidade";
        public static final String EVENT_ACTION_CART_CONCLUDE = "Concluir resgate";
        public static final String EVENT_ACTION_CHECKOUT_ADDRESS_SEND = "Enviar Pedido";

        public static final String EVENT_LABEL_VALIDATION_ERROR = "Erro na validação";
    }
}
