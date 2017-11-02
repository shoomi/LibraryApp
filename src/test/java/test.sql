# SELECT borrowing_date
# FROM ((borrowings
#   INNER JOIN books ON borrowings.book_id = books.book_id)
#   INNER JOIN users ON borrowings.user_id = users.user_id)
# WHERE (users.login = 'shoomi') AND returning_date IS NULL


SELECT
  title,
  author,
  release_date,
  borrowing_date
FROM ((borrowings
  INNER JOIN books ON borrowings.book_id = books.book_id)
  INNER JOIN users ON borrowings.user_id = users.user_id)
WHERE (users.login = 'shoomi') AND returning_date IS NULL