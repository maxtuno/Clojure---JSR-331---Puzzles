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

