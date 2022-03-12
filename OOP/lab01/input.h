#ifndef INPUT_H
#define INPUT_H

#include <stdlib.h>
#include <stdio.h>
#include "points.h"
#include "return_code.h"
#include "figure.h"

return_code read_from_file(FILE *f, figure_t &fig);
return_code read_line_point(FILE *f, point_t &p);
return_code read_line_matrix(FILE *f, size_t &i, size_t &j);

#endif // INPUT_H

