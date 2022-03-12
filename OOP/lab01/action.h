#ifndef ACTION_H
#define ACTION_H

#include "points.h"
#include "figure.h"

typedef struct data data_t;

struct alpha_t
{
    double ax;
    double ay;
    double az;
};

struct data
{
    const char *filename;
    alpha_t rotation;
    struct
    {
        double k;
    } scale;
    struct
    {
        double dx;
        double dz;
        double dy;
    } move;
};

point_t &get_point(figure_t fig, int i);
double get_alphax(alpha_t &d);
double get_alphay(alpha_t &d);
double get_alphaz(alpha_t &d);
double get_deltax(data_t &d);
double get_deltay(data_t &d);
double get_deltaz(data_t &d);
alpha_t get_alpha(data_t &d);
double get_k(data_t &d);
const char *get_filename(data_t &d);

#endif // ACTION_H
