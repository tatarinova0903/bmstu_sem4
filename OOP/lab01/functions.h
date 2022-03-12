#ifndef FUNCTIONS_H
#define FUNCTIONS_H

#include <cmath>
#include "input.h"
#include "mainwindow.h"
#include "points.h"

return_code download_model(figure_t &fig, data_t act);
return_code move_fig(figure_t &fig, data_t act);
return_code rotation_fig(figure_t &fig, data_t act);
return_code scale_fig(figure_t &fig, data_t act);
void clear_fig(figure_t &fig);
void draw_fig(figure_t &fig, myscene_t scene);

void draw_model(figure_t fig, myscene_t scene);
#endif // FUNCTIONS_H

