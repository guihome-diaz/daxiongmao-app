package eu.daxiongmao.core.utils.label;


import eu.daxiongmao.core.model.db.Label;
import eu.daxiongmao.core.model.enums.AppLang;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Excel file writer test
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/04
 */
@Log4j2
public class LabelExcelWriterTest {

    private final LabelExcelWriter excelWriter = new LabelExcelWriter();
    private final LabelExcelReader excelReader = new LabelExcelReader();

    @Test
    public void writeContent() {
        // write test content
        final List<Label> labels = getTestData();
        final Optional<Path> tempFile = excelWriter.exportLabelsToFile(labels);
        Assertions.assertTrue(tempFile.isPresent());
        Assertions.assertTrue(Files.exists(tempFile.get()));

        // Read test content
        final Optional<Set<Label>> labelsFromFile = excelReader.importLabelsFromFile(tempFile.get());
        Assertions.assertTrue(labelsFromFile.isPresent());
        Assertions.assertFalse(labelsFromFile.get().isEmpty());
        Assertions.assertEquals(labels.size(), labelsFromFile.get().size());
        for (Label lbl : labels) {
            log.debug("looking for label code: {}", lbl.getCode());
            Assertions.assertTrue(labelsFromFile.get().contains(lbl));
        }
    }

    private List<Label> getTestData() {
        // dataset
        List<String> input = new ArrayList<>();
        input.add("APP.WELCOME;Bienvenue;Welcome;");
        input.add("HTTP_200;200;OK;200 OK;200元");
        input.add("HTTP_201;201;Créé. La demande a été satisfaite, ce qui a entraîné la création d'une nouvelle ressource;201 Created. The request has been fulfilled, resulting in the creation of a new resource;201 创建了。该请求已得到满足，导致创建了一个新的资源。");
        input.add("HTTP_202;202;Accepté. La demande a été acceptée pour traitement, mais le traitement n'a pas été achevé.;202 Accepted. The request has been accepted for processing, but the processing has not been completed.;202 接受。已受理处理，但未完成处理。");
        input.add("HTTP_400;400;Mauvaise demande. Le serveur ne peut pas ou ne veut pas traiter la demande en raison d'une erreur apparente du client;400 Bad Request. The server cannot or will not process the request due to an apparent client error;400不良请求。由于明显的客户端错误，服务器无法或将无法处理该请求");
        input.add("HTTP_401;401;Non autorisé. L'authentification est requise et a échoué ou n'a pas encore été fournie.;401 Unauthorized. Authentication is required and has failed or has not yet been provided.;401未经授权。需要认证，但已失败或未提供认证。");
        input.add("HTTP_403;403;Interdit. La demande contenait des données valides et a été comprise par le serveur, mais celui-ci refuse d'y donner suite. Cela peut être dû au fait que l'utilisateur n'a pas les autorisations nécessaires pour une ressource ou qu'il a besoin d'un compte quelconque.;403 Forbidden. The request contained valid data and was understood by the server, but the server is refusing action. This may be due to the user not having the necessary permissions for a resource or needing an account of some sort.;403 被禁止。该请求包含有效的数据，并且被服务器理解，但服务器拒绝操作。这可能是由于用户没有资源的必要权限或需要某种类型的账户。");
        input.add("HTTP_404;404;Non trouvé. La ressource demandée n'a pas pu être trouvée mais pourrait être disponible à l'avenir.;404 Not Found. The requested resource could not be found but may be available in the future.;404 未找到。无法找到所请求的资源，但将来可能可以使用。");
        input.add("HTTP_405;405;Méthode non autorisée. Une méthode de demande n'est pas prise en charge pour la ressource demandée ; par exemple, une demande GET sur un formulaire qui nécessite que les données soient présentées par POST, ou une demande PUT sur une ressource en lecture seule.;405 Method Not Allowed. A request method is not supported for the requested resource; for example, a GET request on a form that requires data to be presented via POST, or a PUT request on a read-only resource.;405 方法不允许。不支持所请求的资源的请求方法；例如，在要求通过POST提交数据的表单上提出GET请求，或者在只读资源上提出PUT请求。");
        input.add("HTTP_406;406;Non acceptable. La ressource demandée est capable de générer uniquement du contenu non acceptable selon les en-têtes Accept envoyés dans la demande;406 Not Acceptable. The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request;406 不可接受。被请求的资源只能生成根据请求中发送的Accept头的内容不能接受的内容。");
        input.add("HTTP_407;407;Authentification du mandataire requise. ;407 Proxy Authentication Required. ;407 需要代理认证。");
        input.add("HTTP_408;408;Demande de délai d'attente. ;408 Request Timeout. ;408 请求超时。");
        input.add("HTTP_409;409;Conflit. Indique que la demande n'a pas pu être traitée en raison d'un conflit dans l'état actuel de la ressource, tel qu'un conflit de modification entre plusieurs mises à jour simultanées.;409 Conflict. Indicates that the request could not be processed because of conflict in the current state of the resource, such as an edit conflict between multiple simultaneous updates.;409 冲突。表示由于资源当前状态的冲突，如多个同时更新之间的编辑冲突，导致请求无法处理。");
        input.add("HTTP_410;410;Disparu. Indique que la ressource demandée n'est plus disponible et ne sera plus disponible à nouveau.;410 Gone. Indicates that the resource requested is no longer available and will not be available again;410 消失。表示所请求的资源已不再可用，并且将不再可用。");
        input.add("HTTP_411;411;Longueur requise. La demande ne précise pas la longueur de son contenu, qui est requise par la ressource demandée.;411 Length Required. The request did not specify the length of its content, which is required by the requested resource.;411 要求的长度。该请求没有指定所请求资源所要求的内容长度。");
        input.add("HTTP_412;412;Échec de la condition préalable. Le serveur ne remplit pas l'une des conditions préalables que le demandeur a placées dans les champs de l'en-tête de la demande.;412 Precondition Failed. The server does not meet one of the preconditions that the requester put on the request header fields.;412 前提条件失败。服务器没有满足请求者在请求头字段中的前提条件之一。");
        input.add("HTTP_413;413;Charge utile trop importante. La demande est plus importante que le serveur ne veut ou ne peut traiter;413 Payload Too Large. The request is larger than the server is willing or able to process;413 有效载荷过大。该请求大于服务器愿意或能够处理的内容。");
        input.add("HTTP_414;414;");
        input.add("URI trop long. L'URI fourni était trop long pour que le serveur puisse traiter;414 URI Too Long. The URI provided was too long for the server to process;414 URI太长。提供的URI太长，服务器无法处理。");
        input.add("HTTP_415;415;Type de support non soutenu. L'entité de demande a un type de média que le serveur ou la ressource ne prend pas en charge. ;415 Unsupported Media Type. The request entity has a media type which the server or resource does not support. ;415 不支持的媒体类型。该请求实体具有服务器或资源不支持的媒体类型。");
        input.add("HTTP_422;422;Unprocessable Entity (entité non traitable). La demande était bien formée mais n'a pas pu être suivie en raison d'erreurs sémantiques.;422 Unprocessable Entity. The request was well-formed but was unable to be followed due to semantic errors.;422 无法处理的实体。该请求的形式良好，但由于语义错误而无法被遵循。");
        input.add("HTTP_423;423;Verrouillé. La ressource à laquelle on accède est verrouillée[16].;423 Locked. The resource that is being accessed is locked.[16];423 锁定。被访问的资源被锁定。");
        input.add("HTTP_424;424;Dépendance échouée. La demande a échoué parce qu'elle dépendait d'une autre demande et que cette demande a échoué;424 Failed Dependency. The request failed because it depended on another request and that request failed;424 失败的依赖关系。该请求失败，因为它依赖于另一个请求，而该请求失败了。");
        input.add("HTTP_425;425;Trop tôt. Indique que le serveur n'est pas disposé à prendre le risque de traiter une demande qui pourrait être rejouée.;425 Too Early. Indicates that the server is unwilling to risk processing a request that might be replayed.;425 太早。表示服务器不愿意冒险处理一个可能被重播的请求。");
        input.add("HTTP_429;429;Trop de demandes. L'utilisateur a envoyé trop de demandes dans un laps de temps donné.;429 Too Many Requests. The user has sent too many requests in a given amount of time.;429 太多请求。用户在一定的时间内发送了太多的请求。");
        input.add("HTTP_431;431;Les champs de l'en-tête de la demande sont trop grands. Le serveur ne veut pas traiter la demande parce qu'un champ d'en-tête individuel, ou tous les champs d'en-tête collectivement, sont trop grands.;431 Request Header Fields Too Large. The server is unwilling to process the request because either an individual header field, or all the header fields collectively, are too large.;431 请求头字段太大。服务器不愿意处理请求，因为单个头字段或所有的头字段都太大了。");
        input.add("HTTP_451;451;Indisponible pour des raisons juridiques. Un opérateur de serveur a reçu une demande légale lui demandant de refuser l'accès à une ressource ou à un ensemble de ressources comprenant la ressource demandée.;451 Unavailable For Legal Reasons. A server operator has received a legal demand to deny access to a resource or to a set of resources that includes the requested resource.;451 由于法律原因不可用。服务器操作员收到了合法的要求，拒绝访问一个资源或包括所请求资源的资源集。");

        // Create labels objects
        List<Label> labels = new ArrayList<>();
        for (String data : input) {
            String[] dataContent = StringUtils.split(data, ";");
            Label label = new Label();
            label.setCode(dataContent[0]);
            if (dataContent.length > 1) {
                label.setLang(AppLang.FRENCH, dataContent[1]);
            }
            if (dataContent.length > 2) {
                label.setLang(AppLang.ENGLISH, dataContent[2]);
            }
            if (dataContent.length > 3) {
                label.setLang(AppLang.CHINESE, dataContent[3]);
            }
            labels.add(label);
        }
        return labels;
    }

}
