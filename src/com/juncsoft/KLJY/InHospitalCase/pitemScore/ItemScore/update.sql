if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[t_ScoreComment_Plc]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[t_ScoreComment_Plc]
GO

CREATE TABLE [dbo].[t_ScoreComment_Plc] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[scId] [int] NULL ,
	[caseId] [int] NULL ,
	[tumor] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[ce1] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[ce2] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[ce3] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[ce4] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[ce5] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[ce6] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[cln] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[pm] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[cgrade] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[stage] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

