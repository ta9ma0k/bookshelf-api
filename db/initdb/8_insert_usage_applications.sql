INSERT INTO usage_applications (applicant_id, book_id, status, requested_at, reason, pic_id, completed_at) values
(1, 1, 'RECEIVED', '2022-12-01 05:55:55', E'自分の知識が古くなって来た気がするので、最新のJavaの基礎について学び直したい！\n* hogehoge\n* fugafuga', 3, '2022-12-02 05:55:55'),
(2, 1, 'PIC_ASSIGNED', '2022-12-01 05:55:55', 'Javaについて知りたい', 1, null),
(3, 1, 'PIC_NOT_ASSIGNED', '2022-12-01 05:55:55', 'Javaについて知りたい', null, null);
