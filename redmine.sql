select b.id, b.firstname, b.lastname, sum(a.hours) as hours from users b 
left join time_entries a on a.user_id = b.id and a.spent_on >= '20180201' and a.spent_on <= '20180228' 
where type = 'User' and b.id > 1 and b.status = 1
group by b.id
order by hours desc;
 
 select id, firstname, lastname, bocsType, bocsSkills from users where type = 'User' and id > 1 and status = 1;


 select sum(a.hours) as hours from time_entries a left join users b on a.user_id = b.id where a.spent_on >= '20180201' and a.spent_on <= '20180228';



SELECT * FROM bitnami_redmine.custom_fields;


SELECT * FROM bitnami_redmine.custom_values where custom_field_id = 4 and customized_id = 2600;


select * from issues a left join custom_values b on a.id = b.customized_id where customized_id = 2600; 


select * from time_entries a where a.spent_on >= '20180301' and a.spent_on <= '20180307';

select d.identifier, d.name as 'prjName', a.issue_id, b.subject, c.name as 'taskType', e.firstname, 
e.lastname, a.hours, f.value as 'checkResult', t1.checker as 'checker' from time_entries a 
left join issues b on a.issue_id = b.id
left join issue_categories c on b.category_id = c.id
left join projects d on a.project_id = d.id
left join users e on b.assigned_to_id = e.id
left join custom_values f on b.id = f.customized_id and f.custom_field_id = 4
left join (select a.issue_id as issue_id, f.value as checker from time_entries a 
left join custom_values f on a.issue_id = f.customized_id
where a.spent_on >= '20180301' and a.spent_on <= '20180307' and f.custom_field_id = 5) t1 on t1.issue_id = a.issue_id
where a.spent_on >= '20180301' and a.spent_on <= '20180307' and length(f.value) > 0
group by a.issue_id;




#查询用户的各个项目的任务排期
#先查出所有的开发用户
select id, firstname, lastname from users where type = 'User' and id > 1 and status = 1 and bocsType = 'dev' order by bocsSkills;
 
select b.id as 'prjId', b.name as 'prjName', a.id as 'issue_id', a.start_date, a.due_date from issues a
left join projects b on a.project_id = b.id
where a.status_id in (1,2,4,7) and a.assigned_to_id = 58 and a.start_date <= '20181201' and a.due_date >= NOW()
order by a.start_date, a.due_date;


#查询给定日期期间有任务的项目
        select a.id, a.name from projects a
        left join issues b on b.project_id = a.id
        where b.due_date >= '20180101' and (b.start_date < '20180101' or (b.start_date >= '20180101' and b.start_date <= '20180331'))       
        group by a.id;
        

#查询给定日期期间当前用户有任务的项目
        select a.id, a.name from projects a
        left join issues b on b.project_id = a.id
        where b.assigned_to_id = 5 and b.due_date >= '20180101' and (b.start_date < '20180101' or (b.start_date >= '20180101' and b.start_date <= '20180331'))       
        group by a.id;
        
        
#按项目查询给定日期期间用户的任务
select b.id as 'prjId', b.name as 'prjName', a.id, a.subject, a.start_date, a.due_date from issues a
        left join projects b on a.project_id = b.id
        where b.id = 35 and a.assigned_to_id = 5 and (a.start_date < '20180101' or (a.start_date >= '20180101' and a.start_date <= '20180331')) and a.due_date >= '20180101'       
        order by a.start_date;
        
        
select * from custom_values f
where f.custom_field_id = 5;


select * from issues;

select * from projects;

select * from users;