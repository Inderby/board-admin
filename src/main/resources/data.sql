insert into admin_account (user_id, user_password, role_types, nickname, email, memo, created_at, created_by,
                          modified_at, modified_by)
values ('inderby', '{noop}asdf1234', 'ADMIN', 'Inderby', 'inderby@mail.com', 'I am Inderby.', now(), 'inderby', now(), 'inderby'),
       ('mark', '{noop}asdf1234', 'MANAGER', 'Mark', 'mark@mail.com', 'I am Mark.', now(), 'inderby', now(), 'inderby'),
       ('susan', '{noop}asdf1234', 'MANAGER,DEVELOPER', 'Susan', 'Susan@mail.com', 'I am Susan.', now(), 'inderby', now(),
        'inderby'),
       ('jim', '{noop}asdf1234', 'USER', 'Jim', 'jim@mail.com', 'I am Jim.', now(), 'inderby', now(), 'inderby')
;