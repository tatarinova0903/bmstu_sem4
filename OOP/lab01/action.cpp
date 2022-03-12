#include "action.h"

point_t &get_point(figure_t fig, int i)
{
    return get_array_el(fig, i);
}

double get_alphax(alpha_t &d)
{
    return d.ax;
}

double get_alphay(alpha_t &d)
{
    return d.ay;
}

double get_alphaz(alpha_t &d)
{
    return d.az;
}

alpha_t get_alpha(data_t &d)
{
    return d.rotation;
}

double get_deltax(data_t &d)
{
    return d.move.dx;
}

double get_deltay(data_t &d)
{
    return d.move.dy;
}

double get_deltaz(data_t &d)
{
    return d.move.dy;
}

double get_k(data_t &d)
{
    return d.scale.k;
}

const char *get_filename(data_t &d)
{
    return d.filename;
}
