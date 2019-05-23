# string-calculator
**1. Why parser class was created?**
Because I wanted to separate logic responsible for handling string from part responsible for processing data.

**2. Why parser is private?**
Because I wanted to write a tests from StringCalculator class perspective. I didn't want to split test logic into two 
separate classes. (There was no such requirement in exercise description)

**3. Why Interpreter was not used?**
I came to conclusions that result from parsing was to simple for it.