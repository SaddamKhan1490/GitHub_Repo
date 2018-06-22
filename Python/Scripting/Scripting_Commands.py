############################################################################################################################################################################################################################################################################################################################################
REPL : Read Evaluate Print Loop
DSL : domain-specific language
############################################################################################################################################################################################################################################################################################################################################



# LEGB(Priority Order : Local > Enclosing > Global > Built-in)::Variable scope in python
# Local : Variables defined within a function i.e. Scope of Value of variable is self contained within the function definition, we cant access the local variable outside the function scope i.e. python will throw an Error in case if we try to access any variale which is declared within function body, outside the function scope. But make a note that before throwing an Error python will make sure that the given variable dosent exist inside any of the LEGB scope. Within a function definition if we declare any variable variable as Global then any chnage in the value of this variable within the function definition will start reflecting globally accross entire program.
# Enclosing : Variables defined within local scope of the enclosing function. Enclosing scope is usually associated with the nested function i.e. in case if we are using a vlaue ins ide our function that is not declared locally withing the function definition then python first looks for its definition within any of the enclosing function for which holds the defintion of the given function or else will check for global then built-in scope, but vice-versa doesnt holds true i.e. enclosing function cant access the value of the variable declared inisde the local function. Therefore in order to expose the definition of the local function to the enclosing function what we can do is prefix the local variable with 'nonlocal' keyword.
# Global : Variables defined at top level of the module or Variables explicitly decloared as global using keyword global
# Built-in : Variables existing with the names that are pre-assinged in python i.e. keywords. Ex ->  import builtins; print(dir(built-in)); Min([value_1, value_2, etc...]). In case if we overrite the definition of the built-in function intoour code i.e. locally or globally, then at the time of calling python will give preference to this function over the original definion that is comming from builtin reason being priority og locsl or global scope is higher than that of the built-in scope.



# Genaral commands
python  => Invok Interactive Shell
exit() => Close Interactive Shell
python filename.py => Run the python script
print(help(str)) or print(help(str.lower)) => get the list of the functions applicable on the given string
print(dir(obj))  => get the list of the functions applicable on the given object.
len(obj) => Lenght i.e. number of elements inside the given object
obj.lower() => Lower Case
obj.upper() => Upper Case
obj.count() => Number of successful match of specified character in given string object
obj.find() => Starting Index of specified character in given string object
obj.replace('old','new') => Replace old value wirh the new one in given string object
+ => concatenation operaton for String & Addition operator for Int or Float
'{}, {} Text...'.format(value1, value2) => Externaly Passing values using the place holders i.e. {}
f'{value1}, {value2.upper()} Text...' => Internally Passing values using the place holders i.e. {}
int => Unlike C/C++/Java there is NO restriction in python on size of the number i.e. a = 1000000000000000000000000000000000...., python will store thi snumber until and unless it doesnt runs out of memory. Also we can perform any large calculation in python untill and unless we have memory available with our machines i.e. power = (100000**100000)



# Strings are sequence or group of character present under "double quote" or 'single quote' or """multi-line quote""". Strings are immutable in nature
string => Similar to Java we can concatenate strings in python i.e. Str_1 + Str_2 + Str_3, etc...
string = "" or '' => Python considers the empty string as having the boolean value as False, and vice-versa i.e. non-empty string is considered as having the boolean value as False
len(string_variable) or len("string or string_variable") => Length or Size of string
min(string_variable) => Minium charachter i.e. ASCII or lexicographical, of the existing string_variable will be returned. Ex -> min("This is a cat") -> a
max(string_variable) => Maxium charachter i.e. ASCII or lexicographical, of the existing string_variable will be returned. Ex -> max("This is a cat") -> T
string_variable.count("pattern or sub-string or sub-string_variable", start_index, end_index) or "string".count("patternt", start_index, end_index) => Find the number of occourences of the pattern inside given string. Ex -> a.count("hi", 0, len(a)) -> Will search occourence of hi inside string "a" from index-0 i.e. start, to last-index i.e. len(a), and will return the result
string_variable.center(desired_string_length, "alingment_character") => Aligning the existing string with given character in center. Ex-> a.center(20,"thisistarget") -> ----thisistarget----
string_variable.ljust(desired_string_length, "alingment_character") => Aligning the existing string with given character to left. Ex-> a.ljust(20,"thisistarget") -> --------thisistarget
string_variable.rjust(desired_string_length, "alingment_character") => Aligning the existing string with given character to right. Ex-> a.rjust(20,"thisistarget") -> thisistarget--------
string_variable.strip("alingment_character") => Removes all the leading and trailing chracter mentioned in its "alingment_character" i.e. argument from the existing string. Ex -> "----thisistarget---".strip -> "thisistarget"
string_variable.lstrip("alingment_character") => Removes all the leading chracter mentioned in its "alingment_character" i.e. argument from the existing string. Ex -> "--------thisistarget".strip -> "thisistarget"
string_variable.rstrip("alingment_character") => Removes all the trailing chracter mentioned in its "alingment_character" i.e. argument from the existing string. Ex -> "thisistarget-------".strip -> "thisistarget"
string_variable.isalpha() => Returns boolean True in case all the characters of string are alphabets only. Ex -> "this".isalpha() -> True or "78".isalpha() -> False
string_variable.isalnum() => Returns boolean True in case all the characters of string are either alphabets or number i.e. alphanumericself. Ex-> "abc78".isalnum() -> True or  "abc@1".isalnum() -> False
string_variable.isspace() => Returns boolean True in case string is empty with space. Ex "   ".isspace() -> True or "   a   ".isspace() -> False
string_variable_1.join(string_variable_2) => Join sequence of sting with another string. Ex -> a = ["hi", "this", "is", "python"] & b ="_" ; b.join(a) -> hi_this_is_python
string_variable.find("pattern or sub-string or sub-string_variable", start_index, end_index) => Find occourence of 1st matching position of sub-string within given string within a substring and return -1 in case if string is not found. Ex -> "thisistarget".find("is", 0, len(thisistarget)) -> 2 #1st occourence of "is" in string
string_variable.rfind("pattern or sub-string or sub-string_variable", start_index, end_index) => Find occourence of last matching position of sub-string within given string within a substring  and return -1 in case if string is not found. Ex -> "thisistarget".rfind("is", 0, len(thisistarget)) -> 4 #Last occourence of "is" in string
string_variable.startswith("pattern or sub-string or sub-string_variable") => Find if string begins with mentioned sub-string
string_variable.endswith("pattern or sub-string or sub-string_variable") => Find if string ends with mentioned sub-string
string_variable.isupper() => Returns boolean True in case all the characters of string are in capital and returns False if only few character of entire string are in capital i.e. init-cap
string_variable.islower() => Returns boolean True in case all the characters of string are in lowercase and returns False if only few character of entire string are in lower i.e. init-cap
string_variable.upper() => Returns new string out of existing string_variable having all its charcter in upper case
string_variable.lower() => Returns new string out of existing string_variable having all its charcter in lower case
string_variable.title() => Returns new string out of existing string_variable having only the 1st letter of each word in upper case and rest in lower case i.e. Init-cap
string_variable.swapcase() => Returns new string out of existing string_variable by exchanging all the existing lower case character with upper case and vice-versa for upper case charcter
string_variable.replace(string_to_be_replaced, string_with_which_we_want_to_replace, number_of_occourrences_to_be_replaced) => Returns new string with the specified replaced value at specified number of match. Ex -> "target4target is our target".replace("target", "guest", 2) -> "guest4guest is our target"
string_variable.translate(maketrans(string_variable_1, string_variable_2)) => Map the each element of string_variable_1 to that of string_variable_2 on positional basis. And to get the output we enclose maketrans() within translate(). Ex -> "this is cow".translate(maketrans("tow", "abc")) -> "ahis is cbc"
string_variable.split() => Returns a list with individual word, seperated on the basis of space i.e. ' '
string_variable.split("delimiter") => Returns a list with individual word, seperated on the basis of delimiter i.e. ',' or '|' or etc...



# Variable
1> Variables are case sensitive in python
2> Variables Can be used directly inside the code without any prior initialization.
3> Variables are dynamically typed i.e. we can specify the variable name and it will automatically sense its data type.
4> Mathematical calculations can be performed directly on Variables



# Operators
BODMAS => Numerical operations in python follow this rule while computing results
+ => Addition
- => Substraction
* => Multiplication
/ => Division
// => Floor Division i.e. Stripping decimal part of the result
** => Exponent or Power
% => Modulo i.e. possibility of getting zero remainder on dividing two number
+=, -=, *=, /=, **=, %= => Takes LHS value and perform associated operation with the RHS value
> => Greater than
< => Less than
>= => Greater than equal to
<= => Less than equal to
== => Equal to (compares actual values of the objects, whereas is operator compares the memory address of the given objects, to get the memory address of the object we can use id(object) i.e. id(obj1) == id(obj2))
!= => NOT Equal to
number += 1 => Increment operator of python i.e. Similar to x++
number -= 1 => Decrement operator of python i.e. Similar to x--
(string or number or object) and (string or number or object) => Returns only "string or number or object" which is present or matched inside both the "string or number or object"
(string or number or object) or (string or number or object) => Returns complete "string or number or object" if even a part of the "string or number or object" is matched inside both the "string or number or object"
type(obj) => Data type of the object
len(obj) => Number of elements present inside the given object
id(obj) => Memory address of the object
abs(obj) => Absolute value i.e. un-signed value
any(obj) => Returns boolean True if any one out of the given condition is True i.e. similar to OR operator
all(obj) => Returns boolean True if all of the given condition is True i.e. similar to AND operator
round(obj, places) => Round Offs to Absolute value untill given decimal places
ord("char") => ASCII equivalent of given character
hex(number) => Hexadecimal equivalent of number is returned
oct(number) => Octadecimal equivalent of number is returned
complex(real_part, imaginary_part) => Returns complex number out of given value. Ex -> complex(1,2) -> 1+j2
import operator; operator.add(number_1, number_2) => Add two numbers and return their sum i.e. number_1 + number_2
import operator; operator.sub(number_1, number_2) => Substract two numbers and return their sum i.e. number_1 - number_2
import operator; operator.mul(number_1, number_2) => Multiply two numbers and return their product i.e. i.e. number_1 * number_2
import operator; operator.pow(number_1, number_2) => Returns the power of (number_1^number_2) of the two argument
import operator; operator.mod(number_1, number_2) => Returns teh modulus of the given two argument
import operator; operator.truediv(number_1, number_2) => Returns the Floating point division of the two argument
import operator; operator.floordiv(number_1, number_2) => Returns the Floor of the division i.e. integral value, of the two argument
import operator; operator.le(number_1, number_2) => Returns boolean True in case number_1 is less than or equal to number_2
import operator; operator.lt(number_1, number_2) => Returns boolean True in case number_1 is less than number_2
import operator; operator.eq(number_1, number_2) => Returns boolean True in case number_1 is equal to number_2
import operator; operator.ne(number_1, number_2) => Returns boolean True in case number_1 is not equal to number_2
import operator; operator.ge(number_1, number_2) => Returns boolean True in case number_1 is greater than and equal to number_2
import operator; operator.gt(number_1, number_2) => Returns boolean True in case number_1 is greater than number_2



# Type Conversion
dataType(object) => Casting object of one type to another i.e. int(string), int(string,2) i.e. binary conversion with base 2 or int(string,10) i.e. Integral conversion with base 10, string(int), float(string), etc...
Types of values which returns FALSE:- 1> False, 2>None, 3> Zero of any numeric datatype, 4> Empty string of any object collection i.e. '', "", (), [] 5>Empty mapping of any dictionary{}
list("string") => Coverts string to list. Ex -> list("target") -> ['t','a','r','g','e','t']
tuple("string") => Converts string to tuple. Ex -> tuple("target") -> ('t','a','r','g','e','t')
set("string") => Converts string to set. Ex -> set("targettarget") -> (['t','a','r','g','e','t'])



# List are sequence of homogeneous or heterogeneous Data types which are mutable in nature i.e. values inside the list can be modidfied, so list can be considered as arrays with Heterogeneous data types i.e. string, int, double, object (list, stack, queue, etc...)
[value1, value2, value3, ..., vlaueN] => Creating the list
print(list) => Printing entire list
print(list[1]) => Printing elements of list with index. We can applying slicing for getting intermediate values of the list where value of the start index will be inclusive where as same dosent holds true for the end index. The starting index of the list is 0.
print(len(list)) => Printing lenght of list
if "element" in list => Return boolean True in case element is existing and present inside list
if "element" not in list => Return boolean True in case element is NOT existing and present inside list
list1 + list2 => Return a single list after concatenating two list
list * n => Returns a single list after combining the given list N-times
list.append(value) => Append value at the end of the existing list
list.insert(index, value) => Add the value to existing list at specified index location
list1.extend(list2) => Add only the value of the list2 to the existing list1, unlike append() or insert() which add the list itself as the value.
list.remove(value) => Removes the first occourence of specified value from the list
list.del(list[start:end]) => Deletes all the elements from the list, starting from start_index to end-1_index
list.pop(index) => Deletes the single element from the specified position as argument
list.pop() => Removes the last element from the list, also make a note that this function returns the value which it pop() i.e. we can store that value inside another object
list,clear() => Clear all the elements which are present inside the list
list.reverse() => Prints the result in the reverse order i.e. last item of the list to the first
list.sort() => Sort the elements inside the list in ascending order
list.sort(reverse=True) => Sort the elements of the list in descending order
sorted(list) => Returns the sorted version of the list, Without altering the existing elements of list
min(list) => Minimum element of the list
max(list) => Maximum element of the list
sum(list) => Sum of element of the list
list.count("element") => Returns count of number of occourence of given element inside the list
list.index(value, from_index, to_index) => Returns the index of the given value present inside the list
value in list => Check wether the value is present inside the given list or not
enumerate(list) => Associates the index to the value present inside the list i.e. for index, item in enumerate(list, start = 1)
value.join(list) => Joins each element of the list with the given value
list.split(value) => Splits the value inside the list based on the specified comma separator.
list = [] or list =list() => Creates empty list



# Tuples
1> Most of the methods which are applicable for list are also applicable for the tuple, with only difference that methods which modifies the value of the existing elemenst of the list are NOT applicable for tuple i.e. tuple can only be used where we are only interested in reading and accessing the value of the list
2> Tuple are immutable lists which can be considered as sequential Data types very similar to that of list, but values inside tuple cant be changed or modified
3> Similar to list tuples can also have heterogeneous data types as its values
(value1, value2, value3, ......., valueN) => Creating a Tuple
tuple1=tuple2 => Creating new tuple out of existing Tuple
tuple[index] => Accessing value of tuple at specified index
tuple = () or tuple = tuple() => Used to create the empty tuple
tuple_1 + tuple_2 => New tuple having combined elements or values of both the individual tuple i.e. t1 = (1,2,3) & t2 = ('a','b','c') => t1 + t2 = (1,2,3,'a','b','c')
tuple = (tuple_1, tuple_2) => Nested tuple will have the combined elements or values of both the tuple represented as individual tuple i.e. t1 = (1,2,3) & t2 = ('a') & t3 = ('a','b','c')=> t = (t1,t2,t3) = ((1,2,3),'a',('a','b','c'))
tuple = (value1, value2, value3, ......., valueN) * number_of_repetitions = New tuple withe same repeated values as many number of repetitions specified i.e. t1 = (1,2,3,)*4 = i.e. t1 = (1,2,3,1,2,3,1,2,3,1,2,3)
len(tuple) = Lenght of tuple i.e. numeber of elements present inisde the tuple
tuple(list)= Convert the element of the list into tuple



# Named Tuples : Is a light weight object similar to normal tuple, which provides easier readability.
1> from collections import namedtuple
2> Construct the namedtuple i.e. YourTupleName = namedtuple('YourTupleName', [identifier_1, identifier_2, identifier_3, etc,...])
3> Create tuples using YourTupleName i.e. NewTuple = YourTupleName(value_1, value_2, value_3, etc...)
4> Now we can access this value either using tuple index or identifier_name in collaboration with (.) operator i.e. NewTuple[index i.e. 0-1-2] or NewTuple.identifier_1



# Set are un ordered collection of the values which dosent contains any duplicate value
{value1, value2, value3, ......., valueN} => Creating a Set
value in set => Check wether the given value is present inside the given set or not
set1.intersection(set2) => Returns the common elements present in to both the set
set1.difference(set2) => Returns the element of the set1 which are not present inside the set2
set1.union(set2) => Returns the distinct unique element from both the set
set = set() => Used to create the empty Set i.e. we cant use set = {}, to create an empty set as this will result in creating the empty Dictionary



# Dictionaries work with (unique Key,Value) pair and can be thought of anologous to Hash Maps or Associative Arrays.
1> Its not mandatory that keys must be of homogeneous types
List1 = [key1, key2, key3, etc...], List = [value1, value2, value3, etc...], Dictionary = zip(List1, List2)
{'key1' : 'value1', 'key2' : value2, key3: [value3, value4,etc...] } => Creating a Dictionary
dict['key'] => To get the associated value of the given key from the Dictionary
dict.get('key', default) => To display desired message in case key doesnot exist inside the given Dictionary
'key' in dict => Check wether a given key is present inside dictionary or not.
dict.setDefault('key', default) => To display desired message in case key doesnot exist inside the given Dictionary, but unlike dict.get() it not only displays the message rather also make a new entry inside the existing dictionary with given default (key, value) for which the corresponding entry was not found
dict['key'] = 'value' => Insert element to to Dictionary # In case key already exists then it s value will get updated i.e. single key update being dictionary mutable data type
dict.update({key1 : value1, 'key2' : value2, etc...}) => Add the value i.e. (Key, Value) pair of other dictionary to its own existing set of values and update the value of existingå Dictionary #multi value update in single shot
dict.keys() => List of the keys present inside the dictionary
dict.values() => List of the values present inside the dictionary
dict.items() => All (Key, Value) pair present inside the dictionary, but here the ordering is not guaranteed while displaying the elements # for key, value in dict.items(): print(key, value)
dict.copy() => Copy content of one dictionary into other i.e. dict_2 = dict_1.copy() -> dict_2 will have all the elemnts of dict_1
dict.clear() => Truncating the elements of the dictionary
del dict['key'] => Delete specific key
value = dict.pop('key') => Delete specific key and returns the value associated with it before actually deleting it
len(dict) => Length of the dictionary i.e. number of (keys, value) paris present inside the given Dictionary
str(dict) => Convert elements of dictionary to string



# IF-Else-Elif Conditionals and booleans in python
if condition : success statement => Check wether single particular condition is meet or not. # Condition Operator "==, !=, >, <, >=, <=, is(object identity i.e. check wether the given object exist in the same memory or not)"
if condition : print statement else: print statement => Check wether single particular condition is meet or not, and send the response irrespective of the matched case.
if condition : print statement elif print statement else: print statement => Check wether multi or sereise of condition is meet or not, and send the response irrespective of the matched case.



# Iterators 
1> Iterations can be performed using loops i.e. for or while, over the value present inside String, list, Tuple, etc...
2> Loops are handy when it comes to iterating and applying logic over the seriese of values lying under the given object
3> Pass key word is used to provide empty definition to the loop i.e. creating loop with empty or without any body
4> For - loops are driven by certaing values whereas While - loops are driven by certain conditions
for num in nums: print(num) => Iterating elemnts present inside the List and printing them using FOR - loop.
for num in nums: if (num==3): print(found) break print(num)#BREAK -> if a condition is meet then we want to come out of the loop. Here O/P -> 1, 2, found
for num in nums: if (num==3): print(found) continue print(num)#CONTINUE -> if we skip a value Without breaking or comming out of the loop . Here O/P -> 1, 2, found, 4, 5
for num in nums: for letters in 'abc': print(num, letters) => Iterating value out of nested for-loop
for words in list: for chracters in word: print(characters)=> Iterating characters out of the words present inside list using nested for-loop
for i in range(start_value, (last_value -1)): print i; => printing the value for the given range.
while x < 10: print (x) x+=1 => prints the value of x
while x < 10:  if (num==3): print(found) break print(num) print (x) x+=1 => Prints the value of x before break
while x < 10:  if (num==3): print(found) continue print(num) print (x) x+=1 => Prints the value of x skipping the values mentioned inside continue or print all the values of x except the one mentioned under the continue
CTRL+C is to interrupt th eany infinit eexecution of the Loopss => Ex ->  While True:



# Functions
1> Functions allows us to acheive specific result by writing a code and we can re-use the code without actually writing them again and function re-usability concept is known as 'keep the code dry'
2> Functions take input and produces the result
3> Functions can be called directly just by specifying there name along with the argument list
4> Its NOT mandatory to associate return value inside function definition
def function_name() : pass => Create balnk function without any body using keyword pass
def function_name() : statements return value => Create function with body and return a value
print(function_name()) => Calling the function to get the print output or return_value # make sure to place () after the function name
print(function_name().upper()) => Applying transformation while retreiving the results i.e. returned value
def function_name(arg) :  return '{} '.format(arg) => Passing function argument to the function body # Make sure that at the time of function calling dont forget to specify the argument
def function_name(arg='default_value') :  return '{} '.format(arg) => Passing default_value to the function body in case where function argument is missing # Make sure that at the time of function calling dont forget to specify the argument
def function_name(*args) : print(args) => (*args)Allows us to accept any number of the positional arguments# Ex -> function_name("hi", "hello", "world") will give output as : ('hi', 'hello', 'world') i.e. a tuple
def function_name(*kwargs) : print(kwargs) =>  (*kwargs) Allows us to accept any number of the keyword arguments# Ex -> function_name(key_1="value_1", key_2=value_2) will give output as : ('key_1':'value_1', 'key_2':value_2) i.e. a dictionary
print()function_name(*list_value, **dict_value) => At the time of returning the value while making function call, (*) will unpack all the list  values & (**) will unpack all the dictionary value. # Ex -> list = ["hi", "hello", "world"], dict = {key_1="value_1", key_2=value_2} will give an output as ('hi', 'hello', 'world') & {'key_1':'value_1', 'key_2':value_2} at th e tim eof making function call as function_name(*list, **dict)



# Ex: Python Script
from Library import Module as
import random; random.choice(obj) => Generate random values from the existing object # Other handy modules are : math, datetime, calendar, webbrowser, os (os.getcwd(), os.__file__)
sys.path => List of directories where the Python looks for the Module running under my application.
sys.path.append('Desired Module Path') => Adding new directory path to the existing directory path.
export PYTHONPATH="Directory_Path"



# Slicing i.e. extracting certain subset of elements from the List & String...
list[start:end:step] # Here the index start from zero, so while specifying end value include the "end-1 = index". Ex -> list[0:5] will fetch first 5 elements from the List. We can also use negative indexes to retreive value and start point of the -ve index is -1. Ex -> list [:-1] will fetch all the element of the list starting from zero. We can mix and match the positive and negative indexes. Ex -> list[2:-1] will fetch all the value starting from index 2 till the last. Also we can skip certain values from in betweeen starting and end of a given sequence by specifying step value. Ex-1 -> list[2:-1:2] will return the every next value starting from the first to last, Ex-2 -> list [::-1] Will reverese the order of all the list value.



# List Comprehensions
1> List comprehensions provide a concise way to create lists. List comprehension is an elegant way to define and create list in Python. These lists have often the qualities of sets, but are not in all cases sets.
2> It consists of brackets containing an expression followed by a for clause, then zero or more for or if clauses. The expressions can be anything, meaning you can put in all kinds of objects in lists.
3> The result will be a new list resulting from evaluating the expression in the context of the for and if clauses which follow it.
4> The list comprehension always returns a result list.
5> List comprehension is a complete substitute for the lambda function as well as the functions map(), filter() and reduce(). For most people the syntax of list comprehension is easier to be grasped.
Ex -> Iterator = [n for n in existing_list] : "Square = [n*n for n in existing_list]" == "Square = map(lambda n: n*n, existing_list)" : "Even = [n for n in existing_list if n%2 == 0]" == "Even = filter(lambda n : n%2==0, existing_list)" : 2D_Value = [(value_list1, value_list2, value_list3) for value_list1 in existing_list1 for value_list2 in range(4) for value_list3 in 'abcd']



# Dictionary Comprehensions
List1 = [key1, key2, key3, etc...], List = [value1, value2, value3, etc...], Dictionary = zip(List1, List2)
Dictionary = {key : value for key, value in zip(names, value) if value != x}



# SET Comprehensions
Set = {n for n in existing_list} # Set Comprehensions are surrounded by "{}" whereas List Comprehensions are surrounded by "[]", apart from this rest everything remains unchanged in the syntax.



# Generators : Generators functions allow you to declare a function that behaves like an iterator, i.e. it can be used in a for loop.
# Yield : Normal functions return a single value using return, just like in Java. In Python, however, there is an alternative, called yield. Using yield anywhere in a function makes it a generator.
Gen = (n*n for n in existing_list) # Here we use "(parantheses)" to instead of "[square bracket]"



# Sorting
1> List
list.sort() or list.sort(reverse=True)=> Sort the elements of the original list
sorted(list) or sorted(list, reverse=True) => Returns new list with sorted elements without impacting original list i.e. element of the original list can be stored inside another list
sorted(list, key=abs) => Returns new list with sorted elements based on it absolute value not signature

2> Tuple
sorted(tuple) or sorted(tuple, reverse=True) => Returns new list with sorted elements without impacting original tuple i.e. element of the original tuple can be stored inside another list or tuple

3> Dictionary
sorted(dict) or sorted(dict, reverse=True) => Returns new list with sorted KEYS without impacting original Dictionary

4> Object
sorted(object, key=object_attribute) or sorted(object, key=object_attribute, reverse=True) or sorted(object, key=lambda : object, object.object_attribute) => Returns new list of object based on the sorted Key attribute value, which is part of the object



############################################################################################################################################################################################################################################################################################################################################
