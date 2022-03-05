#ifndef FUNCTIONS_H
#define FUNCTIONS_H

#include "io.h"
#include "mainwindow.h"
#include "points.h"

#define PI 3.14

ReturnCode download_model(figure_t &fig, action_t act);
ReturnCode move_fig(figure_t &fig, action_t act);
ReturnCode rotation_fig(figure_t &fig, action_t act);
ReturnCode scale_fig(figure_t &fig, action_t act);
ReturnCode clear_fig(figure_t &fig);
ReturnCode draw_fig(figure_t &fig, myscene_t scene);
void move(struct point &a, double dx, double dy, double dz);
void scale(struct point &a, struct point m, double k);
void rotation(struct point &a, double ax, double ay, double az);

void draw_model(figure_t fig, myscene_t scene);
#endif // FUNCTIONS_H

