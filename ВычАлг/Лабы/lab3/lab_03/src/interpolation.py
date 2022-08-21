from prettytable import PrettyTable
from src.io import print_data

def interpolate(x_arr, y_arr, argument, c_start=0, c_finish=0, tables=False):
    # Длинна вектора входных значений
    vec_len = len(x_arr)

    # Заполнения массива из 'a' кроме 0-ого элемента
    a_arr = [None]
    for i in range(vec_len - 1):
        a_arr.append(y_arr[i])

    # Заполнения массива из 'h' кроме 0-ого элемента
    h_arr = [None]
    for i in range(1, vec_len):
        h_arr.append(x_arr[i] - x_arr[i-1])

    # Заполняю массив из 'f' на основании массива из 'h'
    # кроме 0-ого и 1-ого элементов
    f_arr = [None, None]
    for i in range(2, vec_len):
        f = 3 * ( (( y_arr[i] - y_arr[i-1] ) / h_arr[i]) - (( y_arr[i-1] - y_arr[i-2] ) / h_arr[i-1]) )
        f_arr.append(f)

    # Из стандартного краевого условия следует, что 'c_arr[1] = 0', а из
    # этого следует, что 'e_arr[2] = 0' и 'n_arr[2] = 0', на основаниии
    # этого заполню их значения
    e_arr = [None, None, 0]
    n_arr = [None, None, c_start]
    for i in range(3, vec_len):
        divider = ( h_arr[i-2] * e_arr[i-1] + 2 * ( h_arr[i-2] + h_arr[i-1] ) )
        e = -h_arr[i-1] / divider
        n = ( f_arr[i-1] - h_arr[i-2] * n_arr[i-1] ) / divider
        e_arr.append(e)
        n_arr.append(n)

    # По условию мы принимаем 'c[N+1] = 0' на основания этого, от верхнего
    # индекса к нижнему, мы вычисляем 'c' кроме 0-ого и 1-ого
    # также по условию 'c[1] = 0'
    c_arr = [None for _ in range(vec_len + 1)]
    c_arr[vec_len] = c_finish
    c_arr[1] = c_start
    for i in range(vec_len - 1, 1, -1):
        divider = ( h_arr[i-1] * e_arr[i] + 2 * ( h_arr[i-1] + h_arr[i] ) )
        c_ = -h_arr[i] * c_arr[i+1] / divider
        _c = f_arr[i] - h_arr[i-1] * n_arr[i]
        c = c_ + _c
        c_arr[i] = c

    # По формуле из лекции заполню 'b'
    b_arr = [None]
    for i in range(1, vec_len-1):
        b_ = (y_arr[i] - y_arr[i-1]) / h_arr[i]
        _b = -h_arr[i] * (c_arr[i+1] + 2 * c_arr[i]) / 3
        b = b_ + _b
        b_arr.append(b)

    b_last_ = (y_arr[vec_len-1] - y_arr[vec_len-2]) / h_arr[vec_len-1]
    _b_last = -h_arr[vec_len-1] * 2 * c_arr[vec_len-1] / 3
    b_last = b_last_ + _b_last
    b_arr.append(b_last)

    # По формуле из лекции заполню 'd'
    d_arr = [None]
    for i in range(1, vec_len-1):
        d = ( c_arr[i+1] - c_arr[i] ) / ( 3 * h_arr[i] )
        d_arr.append(d)

    d_last = ( - c_arr[vec_len-1] ) / ( 3 * h_arr[vec_len-1] )
    d_arr.append(d_last)

    # Поиск промежутка для аргумента 'ind'
    if argument < x_arr[0]:
        return None

    if argument > x_arr[vec_len-1]:
        return None

    signes = list(map(lambda x: x >= argument, x_arr))
    ind = 1
    for i in range(1, vec_len):
        ind = i
        if signes[i-1: i+1] == [False, True]:
            break

    # Поиск значения по формуле из лекции
    result_1 = a_arr[ind]
    result_2 = b_arr[ind] * (argument - x_arr[ind-1])
    result_3 = c_arr[ind] * (argument - x_arr[ind-1])**2
    result_4 = d_arr[ind] * (argument - x_arr[ind-1])**3
    result = result_1 + result_2 + result_3 + result_4

    # Извлекаю 'c[N+1]', для того, чтобы можно было красиво выводить
    # промежуточные результаты [это нужно сделать в самом конце]
    del c_arr[vec_len]

    if tables:
        # Красивый вывод данных
        def my_round(x):
            if x is None:
                return None
            return round(x, 3)

        print_data(
            x = list(map(my_round, x_arr)),
            y = list(map(my_round, y_arr)),
            a = list(map(my_round, a_arr)),
            h = list(map(my_round, h_arr)),
            f = list(map(my_round, f_arr)),
            e = list(map(my_round, e_arr)),
            n = list(map(my_round, n_arr)),
            c = list(map(my_round, c_arr)),
            b = list(map(my_round, b_arr)),
            d = list(map(my_round, d_arr)),
        )

    # Возврат значения
    return result
