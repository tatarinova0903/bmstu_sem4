from sys import argv
from sympy import *

from src.io import input_from_file, print_data
from src.interpolation import interpolate
from src.newton import NewtonPolinom

if __name__ == "__main__":
    filename = 'shared/in.txt'

    if len(argv) == 2:
        filename = argv[1]

    if len(argv) > 2:
        print('wrong arguments - exit')
        exit()

    data = input_from_file(filename)

    if data is None:
        print('wrong file or file content - exit')
        exit()

    print_data(x=data[0], y=data[1])

    argument = float(input('input argument: '))

    newton = NewtonPolinom(data[0], data[1], 3)

    newton_string = newton.find_string(argument)
    x = Symbol('x')
    y = eval(newton_string)
    s = str(y.diff(x))
    yy = eval(s)
    ss = str(yy.diff(x))

    c_start = eval(ss, {'x': data[0][0]})
    c_finish = eval(ss, {'x': data[0][-1]})

    newton_res = newton.find_value(argument)
    first_res = interpolate(data[0], data[1], argument)
    second_res = interpolate(data[0], data[1], argument, c_start=c_start)
    third_res = interpolate(data[0], data[1], argument, c_start=c_start, c_finish=c_finish)

    print_data(
        newton=[newton_res],
        default=[first_res],
        second=[second_res],
        third=[third_res],
    )
