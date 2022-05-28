#include <utility>

#include "model_command.h"

add_model::add_model(std::shared_ptr<object> model)
    : model(std::move(model)) {}

void add_model::execute()
{
     _scene_manager->add_model(model);
}


remove_model::remove_model(const size_t &m)
    : model_n_(m) {}

void remove_model::execute()
{
     _scene_manager->remove_model(model_n_);
}


count_model::count_model(std::shared_ptr<size_t> &count)
    : count_(count) {}

void count_model::execute()
{
    (*count_) = _scene_manager->get_models_count();
}


move_model::move_model(const double &x,
                     const double &y,
                     const double &z,
                     std::shared_ptr<object> model)
: dx(x), dy(y), dz(z), model(model) {}

void move_model::execute()
{
    point move(dx, dy, dz);
    point scale(1, 1, 1);
    point turn(0, 0, 0);

    _reform_manager->reform_object(model, move, scale, turn);
}


scale_model::scale_model(const double &x,
                       const double &y,
                       const double &z,
                       std::shared_ptr<object> model)
    : kx(x), ky(y), kz(z), model(model) {}

void scale_model::execute()
{
    point move(0, 0, 0);
    point scale(kx, ky, kz);
    point turn(0, 0, 0);

    _reform_manager->reform_object(model, move, scale, turn);
}


rotate_model::rotate_model(const double &x,
                         const double &y,
                         const double &z,
                         std::shared_ptr<object> model)
    : ox(x), oy(y), oz(z), model(model) {}

void rotate_model::execute()
{
    point move(0, 0, 0);
    point scale(1, 1, 1);
    point turn(ox, oy, oz);

    _reform_manager->reform_object(model, move, scale, turn);
}


reform_model::reform_model(const point &m,
                         const point &s,
                         const point &t,
                           std::shared_ptr<object> model)
    : move(m), scale(s), turn(t), model(model) {}

void reform_model::execute()
{
    _reform_manager->reform_object(model, move, scale, turn);
}


load_model::load_model(std::string fname)
    : file_name(std::move(fname)) {}

void load_model::execute()
{
    _load_manager->load_model(file_name);
}
