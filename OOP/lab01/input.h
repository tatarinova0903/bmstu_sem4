#ifndef INPUT_H
#define INPUT_H

#include <stdlib.h>
#include <stdio.h>
#include "points.h"
#include "return_code.h"
#include "figure.h"

return_code read_from_file(figure_t &fig, FILE *f);
return_code read_line_point(point_t &p, FILE *f);
return_code read_line_matrix(size_t &i, size_t &j, FILE *f);

#endif // INPUT_H

