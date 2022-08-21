import math

class NewtonDot:
    def __init__(self, x, y):
        self.x = x
        self.y = y

class NewtonPolinom:
    def __init__(self, x_arr, y_arr, degree):
        self.data = [NewtonDot(x_arr[i], y_arr[i]) for i in range(len(x_arr))]
        self.degree = degree

    def _initial_index(self, argument):
        dot_am = self.degree + 2

        if len(self.data) < dot_am:
            return None

        left_side_len = math.ceil(dot_am / 2)
        last_before_index = None

        for i in range(len(self.data)):
            if self.data[i].x < argument:
                last_before_index = i

        if last_before_index is None:
            return 0

        if last_before_index + dot_am - left_side_len < len(self.data):
            return last_before_index - left_side_len + 1

        d = last_before_index + dot_am - left_side_len - len(self.data)

        return last_before_index - left_side_len + 1 - d

    def _create_arrays(self, argument):
        dot_am = self.degree + 1
        ind = self._initial_index(argument)
        # print(f"--->{ind}")
        if ind is None:
            return None

        arguments = list()
        values = list()

        for i in range(ind, ind + dot_am, 1):
            arguments.append(self.data[i].x)
            values.append(self.data[i].y)

        return arguments, values

    def _newton_value(self, argument, arrays):
        # arrays = self._create_arrays(argument)
        if arrays is None:
            return None

        arguments = arrays[0]
        values = [arrays[1]]

        for i in range(self.degree):
            new_slice = list()
            for j in range(self.degree - i):
                val = values[-1][j + 1] - values[-1][j]
                val /= arguments[j + 1 + i] - arguments[j]
                new_slice.append(val)
            values.append(new_slice)

        odds = list()
        for slice in values:
            odds.append(slice[0])

        k = 1
        sum = values[0][0]
        for i in range(self.degree):
            k *= argument - arguments[i]
            sum += odds[i+1] * k

        return sum

    def find_value(self, argument):
        if self.degree < 1:
            return None
        arrays = self._create_arrays(argument)
        if arrays is None:
            return None

        return self._newton_value(argument, arrays)

    def find_root(self):
        if self.degree < 1:
            return None
        arrays = self._create_arrays(0.0)
        if arrays is None:
            return None

        return self._newton_value(0.0, (arrays[1], arrays[0]))

    def _newton_string(self, argument, arrays):
        # arrays = self._create_arrays(argument)
        if arrays is None:
            return None

        arguments = arrays[0]
        values = [arrays[1]]

        for i in range(self.degree):
            new_slice = list()
            for j in range(self.degree - i):
                val = values[-1][j + 1] - values[-1][j]
                val /= arguments[j + 1 + i] - arguments[j]
                new_slice.append(val)
            values.append(new_slice)

        odds = list()
        for slice in values:
            odds.append(slice[0])

        s = f"({values[0][0]})"

        k = '1'
        for i in range(self.degree):
            k += f" * (x - {arguments[i]})"
            s += f" + ({odds[i+1]} * {k})"

        return s

    def find_string(self, argument):
        if self.degree < 1:
            return None
        arrays = self._create_arrays(argument)
        if arrays is None:
            return None

        return self._newton_string(argument, arrays)
