(ns numerology.core-test
  (:require [clojure.test :refer :all]
            [numerology.core :refer :all]))

(deftest testing-process
  (testing "replaces 9 with two tens"
    (is (= [10 10] (process [9])))
    (is (= [0 10 10 0] (process [0 9 0]))))

  (testing "replaces 2 with an equal amount of 1s as the number to the left"
    (is (= [1 1] (process [1 2])))
    (is (= [0] (process [0 2]))))

  (testing "replaces 6 with an equal amount of 3's as the value of the number which is an equal
  amount of steps to the right of the 6 as the number which is to the immediate left of it"
    (is (= [1 3 1] (process [1 6 1])))
    (is (= [0 3 3 3 3 3 3] (process [0 6]))))

  (testing
    (testing "replace any 3 by a 5 if not immediately succeeded by 5"
      (is (= [5] (process [3])))
      (is (= [3 5] (process [3 5])))
      (is (= [5 5] (process [5 3])))
      (is (= [0 5] (process [0 3]))))
    (testing "and any 4 by a 3 if not immediately preceeded by 5."
      (is (= [3] (process [4])))
      (is (= [5 4] (process [5 4])))
      (is (= [3 5] (process [4 5])))
      (is (= [0 3] (process [0 4]))))
    (testing "When replacing the 3's and 4's, you may not replace more than one 3 or 4 in a go
    without having replaced one instance of the other in between."
      (is (= [5 3] (process [3 3])))
      (is (= [3 4] (process [4 4]))))
    (testing "When four 3's have been replaced, and three 4's have been replaced no more replacements may occur until a 7 is seen."
      (is (= [5 3 5 3 5 3 5 4] (process [3 4 3 4 3 4 3 4])))
      (is (= [3 5 3 5 3 5 3 5 4] (process [4 3 4 3 4 3 4 3 4]))))
    (testing "Once a 7 is seen, the whole process is reset and four 3's and three 4's may be replaced again"
      (is (= [5 3 5 3 5 3 5 4 7 5] (process [3 4 3 4 3 4 3 4 7 3])))
      (is (= [3 5 3 5 3 5 3 5 4 7 3] (process [4 3 4 3 4 3 4 3 4 7 4]))))))

(deftest testing-post-process
  (testing "replace the last integer in the input sequence by the lowest next odd number if not already
  odd, when the first number in the input sequence is not even. The mutation of the last integer happens
  after all other rules have been applied, with the basis of the premise from the pristine unmutated version
  of the first integer in the series as it was before any rules where applied to mutate the sequence of numbers."
    (is (= [1 11 9] (post-process [1 11 9] [1 11 9])))
    (is (= [12 11 10] (post-process [12 11 10] [12 11 10])))
    (is (= [1 11 9] (post-process [1 11 10] [1 11 10]))))

  (testing "replace the last integer in the presented input sequence by the immediately ascending successive
  even integer, if not already even, under the precondition that the initial integer in the sequence is not odd.
  This rule is applied at the end after all other rules, and only to be applied in a circumstance where the sequence
  as presented to the program without any rules applied has an even integer as the first number."
    (is (= [12 17 12] (post-process [12 17 12] [12 17 12])))
    (is (= [12 17 6] (post-process [12 17 5] [12 17 5])))))