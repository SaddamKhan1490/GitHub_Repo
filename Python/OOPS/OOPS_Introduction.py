############################################################################################################################################################################################################################################################################################################################################
OOP's in Python:-
############################################################################################################################################################################################################################################################################################################################################



#Classes logically group data and function in such a way that its easy to re-use and build uopon in case it's necessary.
#Class cant be left empty rather we can use the pass keyword instead of keep it empty inorder to avoid error.
#Class is the blue print for creating instances
#Each method in the class automatically thake instance as the first argument withing the class i.e. self. Ex -> def fxn(self): return '{} {}'.format(self.first_variable, self.second variable)
#self is specific to the instance i.e. same method will receive different value of self for different instance, depending on the instance which calls it.

# Creating Class
class Class_Name: statement => Creating Class
instance = Class_Name() => Creating Class Instance
# Creating instance variables
instance.variable_name = value => Creating Instance Variables
print(instance.variable_name) => Extract value of the instance variables
def __init__(self, variable1, variable2, etc...) : statement => Setting up default_value fro the variables i.e. Similar to constructors in Java # so now at the time variable creation itself we can initialise them
# Class Variables are the Variables that are shared among all the instances of the class i.e. instance varibles are unique for given instance, while class variables must be the same for all the instances.
# We cant use class variable directly into some other function or class, we need to prefix it eitehr with the class name or an inctance of a given class i.e. class_name.class_variable_name or self.class_variable_name
class_variable_name = value => Initialising the class variable
# Regular methods in the class the takes instance as the first variable inside the class i.e. self.
# Class methods are the one which don take the instance as its first argument inside the method rather takes the class variable & we can transform the regulatr method to the class method using decorator @Classmethod
# Running the class method with the instance will still result in changing the value of the Globally across all the class instances
@Classmethod def method_name(cls, variable1, variable2, etc) : statement or pass or return#Here the common convention is to take the 'cls' as the class variable name similar to 'self' which is the instance variable
Class.class_variable_name=value is equivalent to cls.class_variable_name=value declared insid ethe method decorated with @Classmethod =>  Changing the value of the variable globally.
# Class methods are the alternative to the constructors
@ Classmethod def method_name(cls, variable1, variable2, etc) : statement ; return cls.class_variable_name or cls.method_name => Creating constructor i.e. initializing value of the class while object creation.
# Static methods are the methods which doesnot pass anything by defalt as the first argument i.e. neither instance nor class. Therefore we can say that Static methods are similar to class methods with exception that they dont have the self as the first function argument in the function definition
@Staticmethod def method_name(variable1, variable2, etc) : statement or pass or return
# Inheritance allows us to inherit i.e. access the Class methods and variables or attributes inside the inherited class from parent class
# Overiding holds good while re-initialising the value i.e. Child class value replace the Parent class value when name of attribute is same in both.
class sub_class_name (parent_class_name): statement or pass => Creating and instantiating the chain of inheritance
help on class Class_Name in module __main__ : => Detailed resolution
# Inheritance allows us to NOT repeat the same logic at multiple places in order to kepe th ecode as simple and maintainable as possible
def method_name(variable1, variable2, variable3, variable4, etc...) : super().__fxn_name__(variable1, variable2, etc...): => Calling Parent class definintion inside Child class using keyword super()
def method_name(self, variable1, variable2, variable3, variable4, etc...) : super().__fxn_name__(variable2, variable3): self.variable1 = value: self.variable4 = value : self.variable5 = value => Specifying half th evalues from parent class thereby leveraging the concept of inheritance and other half by instantiating it manually.



Duck Typing : If we are unable to handle certain check & permissions then these check & permissions should be handled and proper message should be displayed instead of throwing error.
Ex : generators doesnot holds results in memory rather returns i.e. yeilds, one value at a time.



############################################################################################################################################################################################################################################################################################################################################
