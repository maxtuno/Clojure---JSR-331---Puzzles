(ns cpwp.core
  (:import [javax.constraints 
            Problem
            ProblemFactory
            Var]))

(defn solve
  [problem solution]
  (let [solver (.getSolver problem)
        solution-iterator (.solutionIterator solver)]
    (try
      
      (.log problem "Before Constraint Posting")
      (.log problem (.getVars problem))
      
      (solution)
      
      (.log problem "After Constraint Posting")
      (.log problem (.getVars problem))
      
      (.log problem "=== Find Solution:")
      
      (while (.hasNext solution-iterator)
        (.log (.next solution-iterator)))
      
      (.log problem "After Search")
      (.log problem (.getVars problem))
      
      (catch Exception exception
        (.log problem (.getMessage exception))))))

(defn solve-midi
  [problem solution make-music]
  (let [solver (.getSolver problem)
        solution-iterator (.solutionIterator solver)]
    (try
      
      (.log problem "Before Constraint Posting")
      (.log problem (.getVars problem))
      
      (solution)
      
      (.log problem "After Constraint Posting")
      (.log problem (.getVars problem))
      
      (.log problem "=== Find Solution:")
      
      (while (.hasNext solution-iterator)
        (let [next-solution (.next solution-iterator)]
          (make-music next-solution)
          (.log next-solution)))
      
      (.log problem "After Search")
      (.log problem (.getVars problem))
      
      (catch Exception exception
        (.log problem (.getMessage exception))))))

(defn solve-math
  [problem solution math]
  (let [solver (.getSolver problem)
        solution-iterator (.solutionIterator solver)]
    (try
      
      (.log problem "Before Constraint Posting")
      (.log problem (.getVars problem))
      
      (solution)
      
      (.log problem "After Constraint Posting")
      (.log problem (.getVars problem))
      
      (.log problem "=== Find Solution:")
      
      (while (.hasNext solution-iterator)
        (let [next-solution (.next solution-iterator)]
          (math next-solution)
          (.log next-solution)))
      
      (.log problem "After Search")
      (.log problem (.getVars problem))
      
      (catch Exception exception
        (.log problem (.getMessage exception))))))
