
-- HTTP errors codes
INSERT INTO LABELS(LABEL_ID, CODE, LANG_FR, LANG_EN, LANG_ZH, VERSION)
    VALUES (SEQ_LABELS.nextval, 'HTTP_400_BAD_REQUEST', 'Erreur HTTP 400 : arguments manquants / mauvaises conditions / requête non conforme', 'HTTP error 400: missing arguments / wrong conditions / malformed request', 'HTTP 400错误', 1);
INSERT INTO LABELS(LABEL_ID, CODE, LANG_FR, LANG_EN, LANG_ZH, VERSION)
    VALUES (SEQ_LABELS.nextval, 'HTTP_401_UNAUTHORIZED', 'Erreur HTTP 401 : vous devez préalablement vous authentifier avant de pouvoir faire cette requête', 'HTTP error 401: you need to login first before doing this query', 'HTTP 401错误', 1);
INSERT INTO LABELS(LABEL_ID, CODE, LANG_FR, LANG_EN, LANG_ZH, VERSION)
    VALUES (SEQ_LABELS.nextval, 'HTTP_403_FORBIDDEN', 'Erreur HTTP 403: vous ne disposez pas de droits suffisants pour effectuer cette opération' , 'HTTP error 403: You are not allowed to perform the request operation / access to that particular data. Please contact our helpdesk', 'HTTP 403"错误', 1);
INSERT INTO LABELS(LABEL_ID, CODE, LANG_FR, LANG_EN, LANG_ZH, VERSION)
    VALUES (SEQ_LABELS.nextval, 'HTTP_404_NOT_FOUND', 'Erreur HTTP 404: resource non trouvée' , 'HTTP error 404: the requested resource could not be found ', 'HTTP 404"错误', 1);

INSERT INTO LABELS(LABEL_ID, CODE, LANG_FR, LANG_EN, LANG_ZH, VERSION)
    VALUES (SEQ_LABELS.nextval, 'HTTP_500_INTERNAL_SERVER_ERROR', 'Erreur HTTP 500: erreur du traitement de données: le serveur n''est pas en mesure de servir la requête' , 'HTTP error 500: internal server error', 'HTTP 500"错误', 1);