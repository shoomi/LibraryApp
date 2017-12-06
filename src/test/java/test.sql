# SELECT borrowing_date
# FROM ((borrowings
#   INNER JOIN books ON borrowings.book_id = books.book_id)
#   INNER JOIN users ON borrowings.user_id = users.user_id)
# WHERE (users.login = 'shoomi') AND returning_date IS NULL


# SELECT
#   title,
#   author,
#   release_date,
#   borrowing_date
# FROM ((borrowings
#   INNER JOIN books ON borrowings.book_id = books.book_id)
#   INNER JOIN users ON borrowings.user_id = users.user_id)
# WHERE (users.login = 'shoomi') AND returning_date IS NULL

# INSERT INTO mylibrary.`borrowings` (user_id, book_id, borrowing_date) VALUES ((SELECT user_id
#                                                                                FROM users
#                                                                                WHERE login = 'shoomi'),
#                                                                               (SELECT book_id
#                                                                                FROM books
#                                                                                WHERE
#                                                                                  title = 'OPER' AND author = 'R' AND
#                                                                                  release_date = 2000),
#                                                                               '2017-10-29 22:23:27')


# SELECT borrowing_date
# FROM borrowings
#   INNER JOIN books ON borrowings.book_id = books.book_id
# WHERE borrowings.user_id = (SELECT user_id FROM users WHERE login = 'shoomi') AND
#       title = 'The Known World1' and author = '' AND release_date = '2003' AND returning_date IS NULL

# UPDATE mylibrary.borrowings
# SET returning_date = '2017-11-03 00:45:00'
# WHERE user_id = (SELECT user_id
#                  FROM users
#                  WHERE login = 'shoomi') AND book_id = (SELECT book_id
#                                                         FROM books
#                                                         WHERE
#                                                           title = 'The Known World' AND author = 'Edward P Jones' AND
#                                                           release_date = 2003)

# SELECT
#   title,
#   author,
#   release_date
# FROM books
# WHERE title NOT IN (SELECT title
#                     FROM books
#                       INNER JOIN borrowings ON books.book_id = borrowings.book_id
#                     WHERE returning_date IS NULL);


# SELECT (SELECT stock
#         FROM books
#         WHERE title = 'OPER' AND release_date = 2000) - (SELECT count(borrowings.book_id)
#                                                        FROM borrowings
#                                                          INNER JOIN books ON borrowings.book_id = books.book_id
#                                                        WHERE title = 'OPER' AND release_date = 2000 AND
#                                                              returning_date IS NULL);


SELECT
  books.title,
  books.author,
  books.release_date
FROM books
WHERE book_id NOT IN (SELECT DISTINCT borrowings.book_id
                      FROM borrowings
                        INNER JOIN books ON borrowings.book_id = books.book_id
                      WHERE ((SELECT count(book_id)
                              FROM borrowings
                              WHERE borrowings.book_id = books.book_id AND returning_date IS NULL) = (SELECT stock
                                                                                                      FROM books
                                                                                                      WHERE
                                                                                                        books.book_id =
                                                                                                        borrowings.book_id)));


SELECT stock
FROM books
  INNER JOIN borrowings ON books.book_id = borrowings.book_id
WHERE returning_date IS NULL
GROUP BY books.book_id;


SELECT books.title
FROM books
HAVING ((stock - (SELECT count(borrowings.book_id)
                  FROM borrowings
                    INNER JOIN books ON borrowings.book_id = books.book_id
                  WHERE returning_date IS NULL
                  GROUP BY books.book_id)) > 0);

SELECT DISTINCT books.title
FROM borrowings
  INNER JOIN books ON borrowings.book_id = books.book_id
WHERE ((SELECT count(book_id)
        FROM borrowings
        WHERE borrowings.book_id = books.book_id AND returning_date IS NULL) = (SELECT stock
                                                                                FROM books
                                                                                WHERE
                                                                                  books.book_id = borrowings.book_id))



SELECT password from users WHERE login = 'shoomi'