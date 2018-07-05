
#########################################################################################################################################################################
#Developer    : Saddam                                                                                                                                                  #
#Date         : 2016/02/12                                                                                                                                              #
#Description  : This script will  bulid simple classifier                                                                                                               #
#########################################################################################################################################################################

# Import required packages
from sklearn.cross_validation import cross_val_score, train_test_split
import numpy as np
from sklearn import datasets
from sklearn.ensemble import RandomForestClassifier
from sklearn import preprocessing

# Import the iris data into Python
iris = datasets.load_iris()

# Feature scaling
min_max_scaler = preprocessing.MinMaxScaler()
X = min_max_scaler.fit_transform(iris.data)

clf = RandomForestClassifier(n_estimators=200)     

# Test our classifier
scores = cross_val_score(clf, X, iris.target, cv=5)

print("Accuracy: %0.2f (+/- %0.2f)" % (scores.mean(), scores.std() * 2))
