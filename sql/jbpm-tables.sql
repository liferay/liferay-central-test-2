CREATE TABLE JBPM_ACTION
        (
                ID_ bigint NOT NULL auto_increment,
                class CHAR(1) NOT NULL            ,
                NAME_ VARCHAR(255)                ,
                ISPROPAGATIONALLOWED_ bit         ,
                ACTIONEXPRESSION_ VARCHAR(255)    ,
                ISASYNC_ bit                      ,
                REFERENCEDACTION_ bigint          ,
                ACTIONDELEGATION_ bigint          ,
                EVENT_ bigint                     ,
                PROCESSDEFINITION_ bigint         ,
                TIMERNAME_      VARCHAR(255)           ,
                DUEDATE_        VARCHAR(255)           ,
                REPEAT_         VARCHAR(255)           ,
                TRANSITIONNAME_ VARCHAR(255)           ,
                TIMERACTION_ bigint                    ,
                EXPRESSION_ text                       ,
                EVENTINDEX_ INTEGER                    ,
                EXCEPTIONHANDLER_ bigint               ,
                EXCEPTIONHANDLERINDEX_ INTEGER         ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_BYTEARRAY
        (
                ID_ bigint NOT NULL auto_increment,
                NAME_ VARCHAR(255)                ,
                FILEDEFINITION_ bigint            ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_BYTEBLOCK
        (
                PROCESSFILE_ bigint NOT NULL,
                BYTES_ BLOB                 ,
                INDEX_ INTEGER NOT NULL     ,
                PRIMARY KEY (PROCESSFILE_, INDEX_)
        )
        type=InnoDB;
CREATE TABLE JBPM_COMMENT
        (
                ID_ bigint NOT NULL auto_increment,
                VERSION_ INTEGER NOT NULL         ,
                ACTORID_ VARCHAR(255)             ,
                TIME_    DATETIME                 ,
                MESSAGE_ text                     ,
                TOKEN_ bigint                     ,
                TASKINSTANCE_ bigint              ,
                TOKENINDEX_        INTEGER               ,
                TASKINSTANCEINDEX_ INTEGER               ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_DECISIONCONDITIONS
        (
                DECISION_ bigint NOT NULL       ,
                TRANSITIONNAME_ VARCHAR(255)    ,
                EXPRESSION_     VARCHAR(255)    ,
                INDEX_          INTEGER NOT NULL,
                PRIMARY KEY (DECISION_, INDEX_)
        )
        type=InnoDB;
CREATE TABLE JBPM_DELEGATION
        (
                ID_ bigint NOT NULL auto_increment,
                CLASSNAME_ text                   ,
                CONFIGURATION_ text               ,
                CONFIGTYPE_ VARCHAR(255)          ,
                PROCESSDEFINITION_ bigint         ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_EVENT
        (
                ID_ bigint NOT NULL auto_increment,
                EVENTTYPE_ VARCHAR(255)           ,
                TYPE_      CHAR(1)                ,
                GRAPHELEMENT_ bigint              ,
                PROCESSDEFINITION_ bigint         ,
                NODE_ bigint                      ,
                TRANSITION_ bigint                ,
                TASK_ bigint                      ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_EXCEPTIONHANDLER
        (
                ID_ bigint NOT NULL auto_increment,
                EXCEPTIONCLASSNAME_ text          ,
                TYPE_ CHAR(1)                     ,
                GRAPHELEMENT_ bigint              ,
                PROCESSDEFINITION_ bigint         ,
                GRAPHELEMENTINDEX_ INTEGER        ,
                NODE_ bigint                      ,
                TRANSITION_ bigint                ,
                TASK_ bigint                      ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_ID_GROUP
        (
                ID_ bigint NOT NULL auto_increment,
                CLASS_ CHAR(1) NOT NULL           ,
                NAME_  VARCHAR(255)               ,
                TYPE_  VARCHAR(255)               ,
                PARENT_ bigint                    ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_ID_MEMBERSHIP
        (
                ID_ bigint NOT NULL auto_increment,
                CLASS_ CHAR(1) NOT NULL           ,
                NAME_  VARCHAR(255)               ,
                ROLE_  VARCHAR(255)               ,
                USER_ bigint                      ,
                GROUP_ bigint                     ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_ID_PERMISSIONS
        (
                ENTITY_ bigint NOT NULL,
                CLASS_  VARCHAR(255)    ,
                NAME_   VARCHAR(255)    ,
                ACTION_ VARCHAR(255)
        )
        type=InnoDB;
CREATE TABLE JBPM_ID_USER
        (
                ID_ bigint NOT NULL auto_increment,
                CLASS_    CHAR(1) NOT NULL           ,
                NAME_     VARCHAR(255)               ,
                EMAIL_    VARCHAR(255)               ,
                PASSWORD_ VARCHAR(255)               ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_LOG
        (
                ID_ bigint NOT NULL auto_increment,
                CLASS_ CHAR(1) NOT NULL           ,
                INDEX_ INTEGER                    ,
                DATE_  DATETIME                   ,
                TOKEN_ bigint                     ,
                PARENT_ bigint                    ,
                MESSAGE_ text                     ,
                EXCEPTION_ text                   ,
                ACTION_ bigint                    ,
                NODE_ bigint                      ,
                ENTER_ DATETIME                   ,
                LEAVE_ DATETIME                   ,
                DURATION_ bigint                  ,
                NEWLONGVALUE_ bigint              ,
                TRANSITION_ bigint                ,
                CHILD_ bigint                     ,
                SOURCENODE_ bigint                ,
                DESTINATIONNODE_ bigint           ,
                VARIABLEINSTANCE_ bigint          ,
                OLDBYTEARRAY_ bigint              ,
                NEWBYTEARRAY_ bigint              ,
                OLDDATEVALUE_ DATETIME            ,
                NEWDATEVALUE_ DATETIME            ,
                OLDDOUBLEVALUE_ DOUBLE PRECISION  ,
                NEWDOUBLEVALUE_ DOUBLE PRECISION  ,
                OLDLONGIDCLASS_ VARCHAR(255)      ,
                OLDLONGIDVALUE_ bigint            ,
                NEWLONGIDCLASS_ VARCHAR(255)      ,
                NEWLONGIDVALUE_ bigint            ,
                OLDSTRINGIDCLASS_ VARCHAR(255)    ,
                OLDSTRINGIDVALUE_ VARCHAR(255)    ,
                NEWSTRINGIDCLASS_ VARCHAR(255)    ,
                NEWSTRINGIDVALUE_ VARCHAR(255)    ,
                OLDLONGVALUE_ bigint              ,
                OLDSTRINGVALUE_ text              ,
                NEWSTRINGVALUE_ text              ,
                TASKINSTANCE_ bigint              ,
                TASKACTORID_    VARCHAR(255)         ,
                TASKOLDACTORID_ VARCHAR(255)         ,
                SWIMLANEINSTANCE_ bigint             ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_MESSAGE
        (
                ID_ bigint NOT NULL auto_increment,
                CLASS_       CHAR(1) NOT NULL           ,
                DESTINATION_ VARCHAR(255)               ,
                EXCEPTION_ text                         ,
                ISSUSPENDED_ bit                        ,
                TOKEN_ bigint                           ,
                TEXT_ VARCHAR(255)                      ,
                ACTION_ bigint                          ,
                NODE_ bigint                            ,
                TRANSITIONNAME_ VARCHAR(255)            ,
                TASKINSTANCE_ bigint                    ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_MODULEDEFINITION
        (
                ID_ bigint NOT NULL auto_increment,
                CLASS_ CHAR(1) NOT NULL           ,
                NAME_ text                        ,
                PROCESSDEFINITION_ bigint         ,
                STARTTASK_ bigint                 ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_MODULEINSTANCE
        (
                ID_ bigint NOT NULL auto_increment,
                CLASS_ CHAR(1) NOT NULL           ,
                PROCESSINSTANCE_ bigint           ,
                TASKMGMTDEFINITION_ bigint        ,
                NAME_ VARCHAR(255)                ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_NODE
        (
                ID_ bigint NOT NULL auto_increment,
                CLASS_ CHAR(1) NOT NULL           ,
                NAME_  VARCHAR(255)               ,
                PROCESSDEFINITION_ bigint         ,
                ISASYNC_ bit                      ,
                ACTION_ bigint                    ,
                SUPERSTATE_ bigint                ,
                SUBPROCESSDEFINITION_ bigint      ,
                DECISIONEXPRESSION_ VARCHAR(255)  ,
                DECISIONDELEGATION bigint         ,
                SIGNAL_ INTEGER                   ,
                CREATETASKS_ bit                  ,
                ENDTASKS_ bit                     ,
                NODECOLLECTIONINDEX_ INTEGER      ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_POOLEDACTOR
        (
                ID_ bigint NOT NULL auto_increment,
                ACTORID_ VARCHAR(255)             ,
                SWIMLANEINSTANCE_ bigint          ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_PROCESSDEFINITION
        (
                ID_ bigint NOT NULL auto_increment,
                NAME_    VARCHAR(255)                ,
                VERSION_ INTEGER                     ,
                ISTERMINATIONIMPLICIT_ bit           ,
                STARTSTATE_ bigint                   ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_PROCESSINSTANCE
        (
                ID_ bigint NOT NULL auto_increment,
                VERSION_ INTEGER NOT NULL         ,
                START_   DATETIME                 ,
                END_     DATETIME                 ,
                ISSUSPENDED_ bit                  ,
                PROCESSDEFINITION_ bigint         ,
                ROOTTOKEN_ bigint                 ,
                SUPERPROCESSTOKEN_ bigint         ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_RUNTIMEACTION
        (
                ID_ bigint NOT NULL auto_increment,
                VERSION_   INTEGER NOT NULL         ,
                EVENTTYPE_ VARCHAR(255)             ,
                TYPE_      CHAR(1)                  ,
                GRAPHELEMENT_ bigint                ,
                PROCESSINSTANCE_ bigint             ,
                ACTION_ bigint                      ,
                PROCESSINSTANCEINDEX_ INTEGER       ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_SWIMLANE
        (
                ID_ bigint NOT NULL auto_increment,
                NAME_                   VARCHAR(255)                ,
                ACTORIDEXPRESSION_      VARCHAR(255)                ,
                POOLEDACTORSEXPRESSION_ VARCHAR(255)                ,
                ASSIGNMENTDELEGATION_ bigint                        ,
                TASKMGMTDEFINITION_ bigint                          ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_SWIMLANEINSTANCE
        (
                ID_ bigint NOT NULL auto_increment,
                NAME_    VARCHAR(255)                ,
                ACTORID_ VARCHAR(255)                ,
                SWIMLANE_ bigint                     ,
                TASKMGMTINSTANCE_ bigint             ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_TASK
        (
                ID_ bigint NOT NULL auto_increment,
                NAME_ VARCHAR(255)                ,
                PROCESSDEFINITION_ bigint         ,
                DESCRIPTION_ text                 ,
                ISBLOCKING_ bit                   ,
                ISSIGNALLING_ bit                 ,
                DUEDATE_                VARCHAR(255)             ,
                ACTORIDEXPRESSION_      VARCHAR(255)             ,
                POOLEDACTORSEXPRESSION_ VARCHAR(255)             ,
                TASKMGMTDEFINITION_ bigint                       ,
                TASKNODE_ bigint                                 ,
                STARTSTATE_ bigint                               ,
                ASSIGNMENTDELEGATION_ bigint                     ,
                SWIMLANE_ bigint                                 ,
                TASKCONTROLLER_ bigint                           ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_TASKACTORPOOL
        (
                TASKINSTANCE_ bigint NOT NULL,
                POOLEDACTOR_ bigint NOT NULL ,
                PRIMARY KEY (TASKINSTANCE_, POOLEDACTOR_)
        )
        type=InnoDB;
CREATE TABLE JBPM_TASKCONTROLLER
        (
                ID_ bigint NOT NULL auto_increment,
                TASKCONTROLLERDELEGATION_ bigint  ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_TASKINSTANCE
        (
                ID_ bigint NOT NULL auto_increment,
                CLASS_ CHAR(1) NOT NULL           ,
                NAME_  VARCHAR(255)               ,
                DESCRIPTION_ text                 ,
                ACTORID_  VARCHAR(255)             ,
                CREATE_   DATETIME                 ,
                START_    DATETIME                 ,
                END_      DATETIME                 ,
                DUEDATE_  DATETIME                 ,
                PRIORITY_ INTEGER                  ,
                ISCANCELLED_ bit                   ,
                ISSUSPENDED_ bit                   ,
                ISOPEN_ bit                        ,
                ISSIGNALLING_ bit                  ,
                ISBLOCKING_ bit                    ,
                TASK_ bigint                       ,
                TOKEN_ bigint                      ,
                SWIMLANINSTANCE_ bigint            ,
                TASKMGMTINSTANCE_ bigint           ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_TIMER
        (
                ID_ bigint NOT NULL auto_increment,
                NAME_           VARCHAR(255)                ,
                DUEDATE_        DATETIME                    ,
                REPEAT_         VARCHAR(255)                ,
                TRANSITIONNAME_ VARCHAR(255)                ,
                EXCEPTION_ text                             ,
                ISSUSPENDED_ bit                            ,
                ACTION_ bigint                              ,
                TOKEN_ bigint                               ,
                PROCESSINSTANCE_ bigint                     ,
                TASKINSTANCE_ bigint                        ,
                GRAPHELEMENTTYPE_ VARCHAR(255)              ,
                GRAPHELEMENT_ bigint                        ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_TOKEN
        (
                ID_ bigint NOT NULL auto_increment,
                VERSION_      INTEGER NOT NULL         ,
                NAME_         VARCHAR(255)             ,
                START_        DATETIME                 ,
                END_          DATETIME                 ,
                NODEENTER_    DATETIME                 ,
                NEXTLOGINDEX_ INTEGER                  ,
                ISABLETOREACTIVATEPARENT_ bit          ,
                ISTERMINATIONIMPLICIT_ bit             ,
                ISSUSPENDED_ bit                       ,
                NODE_ bigint                           ,
                PROCESSINSTANCE_ bigint                ,
                PARENT_ bigint                         ,
                SUBPROCESSINSTANCE_ bigint             ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_TOKENVARIABLEMAP
        (
                ID_ bigint NOT NULL auto_increment,
                TOKEN_ bigint                     ,
                CONTEXTINSTANCE_ bigint           ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_TRANSITION
        (
                ID_ bigint NOT NULL auto_increment,
                NAME_ VARCHAR(255)                ,
                PROCESSDEFINITION_ bigint         ,
                FROM_ bigint                      ,
                TO_ bigint                        ,
                FROMINDEX_ INTEGER                ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_VARIABLEACCESS
        (
                ID_ bigint NOT NULL auto_increment,
                VARIABLENAME_ VARCHAR(255)        ,
                ACCESS_       VARCHAR(255)        ,
                MAPPEDNAME_   VARCHAR(255)        ,
                PROCESSSTATE_ bigint              ,
                TASKCONTROLLER_ bigint            ,
                INDEX_ INTEGER                    ,
                SCRIPT_ bigint                    ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;
CREATE TABLE JBPM_VARIABLEINSTANCE
        (
                ID_ bigint NOT NULL auto_increment,
                CLASS_     CHAR(1) NOT NULL           ,
                NAME_      VARCHAR(255)               ,
                CONVERTER_ CHAR(1)                    ,
                TOKEN_ bigint                         ,
                TOKENVARIABLEMAP_ bigint              ,
                PROCESSINSTANCE_ bigint               ,
                BYTEARRAYVALUE_ bigint                ,
                DATEVALUE_ DATETIME                   ,
                DOUBLEVALUE_ DOUBLE PRECISION         ,
                LONGIDCLASS_ VARCHAR(255)             ,
                LONGVALUE_ bigint                     ,
                STRINGIDCLASS_ VARCHAR(255)           ,
                STRINGVALUE_   VARCHAR(255)           ,
                TASKINSTANCE_ bigint                  ,
                PRIMARY KEY (ID_)
        )
        type=InnoDB;

ALTER TABLE JBPM_ACTION ADD INDEX FK_ACTION_EVENT
(
        EVENT_
)
,
ADD CONSTRAINT FK_ACTION_EVENT FOREIGN KEY
(
        EVENT_
)
REFERENCES JBPM_EVENT
(
        ID_
)
;
ALTER TABLE JBPM_ACTION ADD INDEX FK_ACTION_EXPTHDL
(
        EXCEPTIONHANDLER_
)
,
ADD CONSTRAINT FK_ACTION_EXPTHDL FOREIGN KEY
(
        EXCEPTIONHANDLER_
)
REFERENCES JBPM_EXCEPTIONHANDLER
(
        ID_
)
;
ALTER TABLE JBPM_ACTION ADD INDEX FK_ACTION_PROCDEF
(
        PROCESSDEFINITION_
)
,
ADD CONSTRAINT FK_ACTION_PROCDEF FOREIGN KEY
(
        PROCESSDEFINITION_
)
REFERENCES JBPM_PROCESSDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_ACTION ADD INDEX FK_CRTETIMERACT_TA
(
        TIMERACTION_
)
,
ADD CONSTRAINT FK_CRTETIMERACT_TA FOREIGN KEY
(
        TIMERACTION_
)
REFERENCES JBPM_ACTION
(
        ID_
)
;
ALTER TABLE JBPM_ACTION ADD INDEX FK_ACTION_ACTNDEL
(
        ACTIONDELEGATION_
)
,
ADD CONSTRAINT FK_ACTION_ACTNDEL FOREIGN KEY
(
        ACTIONDELEGATION_
)
REFERENCES JBPM_DELEGATION
(
        ID_
)
;
ALTER TABLE JBPM_ACTION ADD INDEX FK_ACTION_REFACT
(
        REFERENCEDACTION_
)
,
ADD CONSTRAINT FK_ACTION_REFACT FOREIGN KEY
(
        REFERENCEDACTION_
)
REFERENCES JBPM_ACTION
(
        ID_
)
;
ALTER TABLE JBPM_BYTEARRAY ADD INDEX FK_BYTEARR_FILDEF
(
        FILEDEFINITION_
)
,
ADD CONSTRAINT FK_BYTEARR_FILDEF FOREIGN KEY
(
        FILEDEFINITION_
)
REFERENCES JBPM_MODULEDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_BYTEBLOCK ADD INDEX FK_BYTEBLOCK_FILE
(
        PROCESSFILE_
)
,
ADD CONSTRAINT FK_BYTEBLOCK_FILE FOREIGN KEY
(
        PROCESSFILE_
)
REFERENCES JBPM_BYTEARRAY
(
        ID_
)
;
ALTER TABLE JBPM_COMMENT ADD INDEX FK_COMMENT_TOKEN
(
        TOKEN_
)
,
ADD CONSTRAINT FK_COMMENT_TOKEN FOREIGN KEY
(
        TOKEN_
)
REFERENCES JBPM_TOKEN
(
        ID_
)
;
ALTER TABLE JBPM_COMMENT ADD INDEX FK_COMMENT_TSK
(
        TASKINSTANCE_
)
,
ADD CONSTRAINT FK_COMMENT_TSK FOREIGN KEY
(
        TASKINSTANCE_
)
REFERENCES JBPM_TASKINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_DECISIONCONDITIONS ADD INDEX FK_DECCOND_DEC
(
        DECISION_
)
,
ADD CONSTRAINT FK_DECCOND_DEC FOREIGN KEY
(
        DECISION_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_DELEGATION ADD INDEX FK_DELEGATION_PRCD
(
        PROCESSDEFINITION_
)
,
ADD CONSTRAINT FK_DELEGATION_PRCD FOREIGN KEY
(
        PROCESSDEFINITION_
)
REFERENCES JBPM_PROCESSDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_EVENT ADD INDEX FK_EVENT_PROCDEF
(
        PROCESSDEFINITION_
)
,
ADD CONSTRAINT FK_EVENT_PROCDEF FOREIGN KEY
(
        PROCESSDEFINITION_
)
REFERENCES JBPM_PROCESSDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_EVENT ADD INDEX FK_EVENT_NODE
(
        NODE_
)
,
ADD CONSTRAINT FK_EVENT_NODE FOREIGN KEY
(
        NODE_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_EVENT ADD INDEX FK_EVENT_TRANS
(
        TRANSITION_
)
,
ADD CONSTRAINT FK_EVENT_TRANS FOREIGN KEY
(
        TRANSITION_
)
REFERENCES JBPM_TRANSITION
(
        ID_
)
;
ALTER TABLE JBPM_EVENT ADD INDEX FK_EVENT_TASK
(
        TASK_
)
,
ADD CONSTRAINT FK_EVENT_TASK FOREIGN KEY
(
        TASK_
)
REFERENCES JBPM_TASK
(
        ID_
)
;
ALTER TABLE JBPM_ID_GROUP ADD INDEX FK_ID_GRP_PARENT
(
        PARENT_
)
,
ADD CONSTRAINT FK_ID_GRP_PARENT FOREIGN KEY
(
        PARENT_
)
REFERENCES JBPM_ID_GROUP
(
        ID_
)
;
ALTER TABLE JBPM_ID_MEMBERSHIP ADD INDEX FK_ID_MEMSHIP_GRP
(
        GROUP_
)
,
ADD CONSTRAINT FK_ID_MEMSHIP_GRP FOREIGN KEY
(
        GROUP_
)
REFERENCES JBPM_ID_GROUP
(
        ID_
)
;
ALTER TABLE JBPM_ID_MEMBERSHIP ADD INDEX FK_ID_MEMSHIP_USR
(
        USER_
)
,
ADD CONSTRAINT FK_ID_MEMSHIP_USR FOREIGN KEY
(
        USER_
)
REFERENCES JBPM_ID_USER
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_SOURCENODE
(
        SOURCENODE_
)
,
ADD CONSTRAINT FK_LOG_SOURCENODE FOREIGN KEY
(
        SOURCENODE_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_TOKEN
(
        TOKEN_
)
,
ADD CONSTRAINT FK_LOG_TOKEN FOREIGN KEY
(
        TOKEN_
)
REFERENCES JBPM_TOKEN
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_OLDBYTES
(
        OLDBYTEARRAY_
)
,
ADD CONSTRAINT FK_LOG_OLDBYTES FOREIGN KEY
(
        OLDBYTEARRAY_
)
REFERENCES JBPM_BYTEARRAY
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_NEWBYTES
(
        NEWBYTEARRAY_
)
,
ADD CONSTRAINT FK_LOG_NEWBYTES FOREIGN KEY
(
        NEWBYTEARRAY_
)
REFERENCES JBPM_BYTEARRAY
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_CHILDTOKEN
(
        CHILD_
)
,
ADD CONSTRAINT FK_LOG_CHILDTOKEN FOREIGN KEY
(
        CHILD_
)
REFERENCES JBPM_TOKEN
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_DESTNODE
(
        DESTINATIONNODE_
)
,
ADD CONSTRAINT FK_LOG_DESTNODE FOREIGN KEY
(
        DESTINATIONNODE_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_TASKINST
(
        TASKINSTANCE_
)
,
ADD CONSTRAINT FK_LOG_TASKINST FOREIGN KEY
(
        TASKINSTANCE_
)
REFERENCES JBPM_TASKINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_SWIMINST
(
        SWIMLANEINSTANCE_
)
,
ADD CONSTRAINT FK_LOG_SWIMINST FOREIGN KEY
(
        SWIMLANEINSTANCE_
)
REFERENCES JBPM_SWIMLANEINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_PARENT
(
        PARENT_
)
,
ADD CONSTRAINT FK_LOG_PARENT FOREIGN KEY
(
        PARENT_
)
REFERENCES JBPM_LOG
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_NODE
(
        NODE_
)
,
ADD CONSTRAINT FK_LOG_NODE FOREIGN KEY
(
        NODE_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_ACTION
(
        ACTION_
)
,
ADD CONSTRAINT FK_LOG_ACTION FOREIGN KEY
(
        ACTION_
)
REFERENCES JBPM_ACTION
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_VARINST
(
        VARIABLEINSTANCE_
)
,
ADD CONSTRAINT FK_LOG_VARINST FOREIGN KEY
(
        VARIABLEINSTANCE_
)
REFERENCES JBPM_VARIABLEINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_LOG ADD INDEX FK_LOG_TRANSITION
(
        TRANSITION_
)
,
ADD CONSTRAINT FK_LOG_TRANSITION FOREIGN KEY
(
        TRANSITION_
)
REFERENCES JBPM_TRANSITION
(
        ID_
)
;
ALTER TABLE JBPM_MESSAGE ADD INDEX FK_MSG_TOKEN
(
        TOKEN_
)
,
ADD CONSTRAINT FK_MSG_TOKEN FOREIGN KEY
(
        TOKEN_
)
REFERENCES JBPM_TOKEN
(
        ID_
)
;
ALTER TABLE JBPM_MESSAGE ADD INDEX FK_CMD_NODE
(
        NODE_
)
,
ADD CONSTRAINT FK_CMD_NODE FOREIGN KEY
(
        NODE_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_MESSAGE ADD INDEX FK_CMD_ACTION
(
        ACTION_
)
,
ADD CONSTRAINT FK_CMD_ACTION FOREIGN KEY
(
        ACTION_
)
REFERENCES JBPM_ACTION
(
        ID_
)
;
ALTER TABLE JBPM_MESSAGE ADD INDEX FK_CMD_TASKINST
(
        TASKINSTANCE_
)
,
ADD CONSTRAINT FK_CMD_TASKINST FOREIGN KEY
(
        TASKINSTANCE_
)
REFERENCES JBPM_TASKINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_MODULEDEFINITION ADD INDEX FK_TSKDEF_START
(
        STARTTASK_
)
,
ADD CONSTRAINT FK_TSKDEF_START FOREIGN KEY
(
        STARTTASK_
)
REFERENCES JBPM_TASK
(
        ID_
)
;
ALTER TABLE JBPM_MODULEDEFINITION ADD INDEX FK_MODDEF_PROCDEF
(
        PROCESSDEFINITION_
)
,
ADD CONSTRAINT FK_MODDEF_PROCDEF FOREIGN KEY
(
        PROCESSDEFINITION_
)
REFERENCES JBPM_PROCESSDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_MODULEINSTANCE ADD INDEX FK_TASKMGTINST_TMD
(
        TASKMGMTDEFINITION_
)
,
ADD CONSTRAINT FK_TASKMGTINST_TMD FOREIGN KEY
(
        TASKMGMTDEFINITION_
)
REFERENCES JBPM_MODULEDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_MODULEINSTANCE ADD INDEX FK_MODINST_PRCINST
(
        PROCESSINSTANCE_
)
,
ADD CONSTRAINT FK_MODINST_PRCINST FOREIGN KEY
(
        PROCESSINSTANCE_
)
REFERENCES JBPM_PROCESSINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_NODE ADD INDEX FK_PROCST_SBPRCDEF
(
        SUBPROCESSDEFINITION_
)
,
ADD CONSTRAINT FK_PROCST_SBPRCDEF FOREIGN KEY
(
        SUBPROCESSDEFINITION_
)
REFERENCES JBPM_PROCESSDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_NODE ADD INDEX FK_NODE_PROCDEF
(
        PROCESSDEFINITION_
)
,
ADD CONSTRAINT FK_NODE_PROCDEF FOREIGN KEY
(
        PROCESSDEFINITION_
)
REFERENCES JBPM_PROCESSDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_NODE ADD INDEX FK_NODE_ACTION
(
        ACTION_
)
,
ADD CONSTRAINT FK_NODE_ACTION FOREIGN KEY
(
        ACTION_
)
REFERENCES JBPM_ACTION
(
        ID_
)
;
ALTER TABLE JBPM_NODE ADD INDEX FK_DECISION_DELEG
(
        DECISIONDELEGATION
)
,
ADD CONSTRAINT FK_DECISION_DELEG FOREIGN KEY
(
        DECISIONDELEGATION
)
REFERENCES JBPM_DELEGATION
(
        ID_
)
;
ALTER TABLE JBPM_NODE ADD INDEX FK_NODE_SUPERSTATE
(
        SUPERSTATE_
)
,
ADD CONSTRAINT FK_NODE_SUPERSTATE FOREIGN KEY
(
        SUPERSTATE_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
CREATE INDEX IDX_PLDACTR_ACTID ON JBPM_POOLEDACTOR
        (
                ACTORID_
        );

ALTER TABLE JBPM_POOLEDACTOR ADD INDEX FK_POOLEDACTOR_SLI
(
        SWIMLANEINSTANCE_
)
,
ADD CONSTRAINT FK_POOLEDACTOR_SLI FOREIGN KEY
(
        SWIMLANEINSTANCE_
)
REFERENCES JBPM_SWIMLANEINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_PROCESSDEFINITION ADD INDEX FK_PROCDEF_STRTSTA
(
        STARTSTATE_
)
,
ADD CONSTRAINT FK_PROCDEF_STRTSTA FOREIGN KEY
(
        STARTSTATE_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_PROCESSINSTANCE ADD INDEX FK_PROCIN_PROCDEF
(
        PROCESSDEFINITION_
)
,
ADD CONSTRAINT FK_PROCIN_PROCDEF FOREIGN KEY
(
        PROCESSDEFINITION_
)
REFERENCES JBPM_PROCESSDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_PROCESSINSTANCE ADD INDEX FK_PROCIN_ROOTTKN
(
        ROOTTOKEN_
)
,
ADD CONSTRAINT FK_PROCIN_ROOTTKN FOREIGN KEY
(
        ROOTTOKEN_
)
REFERENCES JBPM_TOKEN
(
        ID_
)
;
ALTER TABLE JBPM_PROCESSINSTANCE ADD INDEX FK_PROCIN_SPROCTKN
(
        SUPERPROCESSTOKEN_
)
,
ADD CONSTRAINT FK_PROCIN_SPROCTKN FOREIGN KEY
(
        SUPERPROCESSTOKEN_
)
REFERENCES JBPM_TOKEN
(
        ID_
)
;
ALTER TABLE JBPM_RUNTIMEACTION ADD INDEX FK_RTACTN_PROCINST
(
        PROCESSINSTANCE_
)
,
ADD CONSTRAINT FK_RTACTN_PROCINST FOREIGN KEY
(
        PROCESSINSTANCE_
)
REFERENCES JBPM_PROCESSINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_RUNTIMEACTION ADD INDEX FK_RTACTN_ACTION
(
        ACTION_
)
,
ADD CONSTRAINT FK_RTACTN_ACTION FOREIGN KEY
(
        ACTION_
)
REFERENCES JBPM_ACTION
(
        ID_
)
;
ALTER TABLE JBPM_SWIMLANE ADD INDEX FK_SWL_ASSDEL
(
        ASSIGNMENTDELEGATION_
)
,
ADD CONSTRAINT FK_SWL_ASSDEL FOREIGN KEY
(
        ASSIGNMENTDELEGATION_
)
REFERENCES JBPM_DELEGATION
(
        ID_
)
;
ALTER TABLE JBPM_SWIMLANE ADD INDEX FK_SWL_TSKMGMTDEF
(
        TASKMGMTDEFINITION_
)
,
ADD CONSTRAINT FK_SWL_TSKMGMTDEF FOREIGN KEY
(
        TASKMGMTDEFINITION_
)
REFERENCES JBPM_MODULEDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_SWIMLANEINSTANCE ADD INDEX FK_SWIMLANEINST_TM
(
        TASKMGMTINSTANCE_
)
,
ADD CONSTRAINT FK_SWIMLANEINST_TM FOREIGN KEY
(
        TASKMGMTINSTANCE_
)
REFERENCES JBPM_MODULEINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_SWIMLANEINSTANCE ADD INDEX FK_SWIMLANEINST_SL
(
        SWIMLANE_
)
,
ADD CONSTRAINT FK_SWIMLANEINST_SL FOREIGN KEY
(
        SWIMLANE_
)
REFERENCES JBPM_SWIMLANE
(
        ID_
)
;
ALTER TABLE JBPM_TASK ADD INDEX FK_TSK_TSKCTRL
(
        TASKCONTROLLER_
)
,
ADD CONSTRAINT FK_TSK_TSKCTRL FOREIGN KEY
(
        TASKCONTROLLER_
)
REFERENCES JBPM_TASKCONTROLLER
(
        ID_
)
;
ALTER TABLE JBPM_TASK ADD INDEX FK_TASK_ASSDEL
(
        ASSIGNMENTDELEGATION_
)
,
ADD CONSTRAINT FK_TASK_ASSDEL FOREIGN KEY
(
        ASSIGNMENTDELEGATION_
)
REFERENCES JBPM_DELEGATION
(
        ID_
)
;
ALTER TABLE JBPM_TASK ADD INDEX FK_TASK_TASKNODE
(
        TASKNODE_
)
,
ADD CONSTRAINT FK_TASK_TASKNODE FOREIGN KEY
(
        TASKNODE_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_TASK ADD INDEX FK_TASK_PROCDEF
(
        PROCESSDEFINITION_
)
,
ADD CONSTRAINT FK_TASK_PROCDEF FOREIGN KEY
(
        PROCESSDEFINITION_
)
REFERENCES JBPM_PROCESSDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_TASK ADD INDEX FK_TASK_STARTST
(
        STARTSTATE_
)
,
ADD CONSTRAINT FK_TASK_STARTST FOREIGN KEY
(
        STARTSTATE_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_TASK ADD INDEX FK_TASK_TASKMGTDEF
(
        TASKMGMTDEFINITION_
)
,
ADD CONSTRAINT FK_TASK_TASKMGTDEF FOREIGN KEY
(
        TASKMGMTDEFINITION_
)
REFERENCES JBPM_MODULEDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_TASK ADD INDEX FK_TASK_SWIMLANE
(
        SWIMLANE_
)
,
ADD CONSTRAINT FK_TASK_SWIMLANE FOREIGN KEY
(
        SWIMLANE_
)
REFERENCES JBPM_SWIMLANE
(
        ID_
)
;
ALTER TABLE JBPM_TASKACTORPOOL ADD INDEX FK_TSKACTPOL_PLACT
(
        POOLEDACTOR_
)
,
ADD CONSTRAINT FK_TSKACTPOL_PLACT FOREIGN KEY
(
        POOLEDACTOR_
)
REFERENCES JBPM_POOLEDACTOR
(
        ID_
)
;
ALTER TABLE JBPM_TASKACTORPOOL ADD INDEX FK_TASKACTPL_TSKI
(
        TASKINSTANCE_
)
,
ADD CONSTRAINT FK_TASKACTPL_TSKI FOREIGN KEY
(
        TASKINSTANCE_
)
REFERENCES JBPM_TASKINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_TASKCONTROLLER ADD INDEX FK_TSKCTRL_DELEG
(
        TASKCONTROLLERDELEGATION_
)
,
ADD CONSTRAINT FK_TSKCTRL_DELEG FOREIGN KEY
(
        TASKCONTROLLERDELEGATION_
)
REFERENCES JBPM_DELEGATION
(
        ID_
)
;
CREATE INDEX IDX_TASK_ACTORID ON JBPM_TASKINSTANCE
        (
                ACTORID_
        );

ALTER TABLE JBPM_TASKINSTANCE ADD INDEX FK_TASKINST_TMINST
(
        TASKMGMTINSTANCE_
)
,
ADD CONSTRAINT FK_TASKINST_TMINST FOREIGN KEY
(
        TASKMGMTINSTANCE_
)
REFERENCES JBPM_MODULEINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_TASKINSTANCE ADD INDEX FK_TASKINST_TOKEN
(
        TOKEN_
)
,
ADD CONSTRAINT FK_TASKINST_TOKEN FOREIGN KEY
(
        TOKEN_
)
REFERENCES JBPM_TOKEN
(
        ID_
)
;
ALTER TABLE JBPM_TASKINSTANCE ADD INDEX FK_TASKINST_SLINST
(
        SWIMLANINSTANCE_
)
,
ADD CONSTRAINT FK_TASKINST_SLINST FOREIGN KEY
(
        SWIMLANINSTANCE_
)
REFERENCES JBPM_SWIMLANEINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_TASKINSTANCE ADD INDEX FK_TASKINST_TASK
(
        TASK_
)
,
ADD CONSTRAINT FK_TASKINST_TASK FOREIGN KEY
(
        TASK_
)
REFERENCES JBPM_TASK
(
        ID_
)
;
ALTER TABLE JBPM_TIMER ADD INDEX FK_TIMER_TOKEN
(
        TOKEN_
)
,
ADD CONSTRAINT FK_TIMER_TOKEN FOREIGN KEY
(
        TOKEN_
)
REFERENCES JBPM_TOKEN
(
        ID_
)
;
ALTER TABLE JBPM_TIMER ADD INDEX FK_TIMER_PRINST
(
        PROCESSINSTANCE_
)
,
ADD CONSTRAINT FK_TIMER_PRINST FOREIGN KEY
(
        PROCESSINSTANCE_
)
REFERENCES JBPM_PROCESSINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_TIMER ADD INDEX FK_TIMER_ACTION
(
        ACTION_
)
,
ADD CONSTRAINT FK_TIMER_ACTION FOREIGN KEY
(
        ACTION_
)
REFERENCES JBPM_ACTION
(
        ID_
)
;
ALTER TABLE JBPM_TIMER ADD INDEX FK_TIMER_TSKINST
(
        TASKINSTANCE_
)
,
ADD CONSTRAINT FK_TIMER_TSKINST FOREIGN KEY
(
        TASKINSTANCE_
)
REFERENCES JBPM_TASKINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_TOKEN ADD INDEX FK_TOKEN_PARENT
(
        PARENT_
)
,
ADD CONSTRAINT FK_TOKEN_PARENT FOREIGN KEY
(
        PARENT_
)
REFERENCES JBPM_TOKEN
(
        ID_
)
;
ALTER TABLE JBPM_TOKEN ADD INDEX FK_TOKEN_NODE
(
        NODE_
)
,
ADD CONSTRAINT FK_TOKEN_NODE FOREIGN KEY
(
        NODE_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_TOKEN ADD INDEX FK_TOKEN_PROCINST
(
        PROCESSINSTANCE_
)
,
ADD CONSTRAINT FK_TOKEN_PROCINST FOREIGN KEY
(
        PROCESSINSTANCE_
)
REFERENCES JBPM_PROCESSINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_TOKEN ADD INDEX FK_TOKEN_SUBPI
(
        SUBPROCESSINSTANCE_
)
,
ADD CONSTRAINT FK_TOKEN_SUBPI FOREIGN KEY
(
        SUBPROCESSINSTANCE_
)
REFERENCES JBPM_PROCESSINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_TOKENVARIABLEMAP ADD INDEX FK_TKVARMAP_CTXT
(
        CONTEXTINSTANCE_
)
,
ADD CONSTRAINT FK_TKVARMAP_CTXT FOREIGN KEY
(
        CONTEXTINSTANCE_
)
REFERENCES JBPM_MODULEINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_TOKENVARIABLEMAP ADD INDEX FK_TKVARMAP_TOKEN
(
        TOKEN_
)
,
ADD CONSTRAINT FK_TKVARMAP_TOKEN FOREIGN KEY
(
        TOKEN_
)
REFERENCES JBPM_TOKEN
(
        ID_
)
;
ALTER TABLE JBPM_TRANSITION ADD INDEX FK_TRANSITION_TO
(
        TO_
)
,
ADD CONSTRAINT FK_TRANSITION_TO FOREIGN KEY
(
        TO_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_TRANSITION ADD INDEX FK_TRANS_PROCDEF
(
        PROCESSDEFINITION_
)
,
ADD CONSTRAINT FK_TRANS_PROCDEF FOREIGN KEY
(
        PROCESSDEFINITION_
)
REFERENCES JBPM_PROCESSDEFINITION
(
        ID_
)
;
ALTER TABLE JBPM_TRANSITION ADD INDEX FK_TRANSITION_FROM
(
        FROM_
)
,
ADD CONSTRAINT FK_TRANSITION_FROM FOREIGN KEY
(
        FROM_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_VARIABLEACCESS ADD INDEX FK_VARACC_TSKCTRL
(
        TASKCONTROLLER_
)
,
ADD CONSTRAINT FK_VARACC_TSKCTRL FOREIGN KEY
(
        TASKCONTROLLER_
)
REFERENCES JBPM_TASKCONTROLLER
(
        ID_
)
;
ALTER TABLE JBPM_VARIABLEACCESS ADD INDEX FK_VARACC_SCRIPT
(
        SCRIPT_
)
,
ADD CONSTRAINT FK_VARACC_SCRIPT FOREIGN KEY
(
        SCRIPT_
)
REFERENCES JBPM_ACTION
(
        ID_
)
;
ALTER TABLE JBPM_VARIABLEACCESS ADD INDEX FK_VARACC_PROCST
(
        PROCESSSTATE_
)
,
ADD CONSTRAINT FK_VARACC_PROCST FOREIGN KEY
(
        PROCESSSTATE_
)
REFERENCES JBPM_NODE
(
        ID_
)
;
ALTER TABLE JBPM_VARIABLEINSTANCE ADD INDEX FK_VARINST_TK
(
        TOKEN_
)
,
ADD CONSTRAINT FK_VARINST_TK FOREIGN KEY
(
        TOKEN_
)
REFERENCES JBPM_TOKEN
(
        ID_
)
;
ALTER TABLE JBPM_VARIABLEINSTANCE ADD INDEX FK_VARINST_TKVARMP
(
        TOKENVARIABLEMAP_
)
,
ADD CONSTRAINT FK_VARINST_TKVARMP FOREIGN KEY
(
        TOKENVARIABLEMAP_
)
REFERENCES JBPM_TOKENVARIABLEMAP
(
        ID_
)
;
ALTER TABLE JBPM_VARIABLEINSTANCE ADD INDEX FK_VARINST_PRCINST
(
        PROCESSINSTANCE_
)
,
ADD CONSTRAINT FK_VARINST_PRCINST FOREIGN KEY
(
        PROCESSINSTANCE_
)
REFERENCES JBPM_PROCESSINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_VARIABLEINSTANCE ADD INDEX FK_VAR_TSKINST
(
        TASKINSTANCE_
)
,
ADD CONSTRAINT FK_VAR_TSKINST FOREIGN KEY
(
        TASKINSTANCE_
)
REFERENCES JBPM_TASKINSTANCE
(
        ID_
)
;
ALTER TABLE JBPM_VARIABLEINSTANCE ADD INDEX FK_BYTEINST_ARRAY
(
        BYTEARRAYVALUE_
)
,
ADD CONSTRAINT FK_BYTEINST_ARRAY FOREIGN KEY
(
        BYTEARRAYVALUE_
)
REFERENCES JBPM_BYTEARRAY
(
        ID_
)
;