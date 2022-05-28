#include <utility>

#include "model_command.h"
#include "reform_manager_creator.h"
#include "scene_manager_creator.h"
#include "load_manager_cretor.h"
#include "loader.h"

move_model::move_model(const double &x,
                     const double &y,
                     const double &z,
                     const size_t &n)
    : dx(x), dy(y), dz(z), model_n_(n) {}

void move_model::execute()
{
    point move(dx, dy, dz);
    point scale(1, 1, 1);
    point turn(0, 0, 0);

    std::shared_ptr<object> model = scene_manager_creator().create_man()->get_scene()->get_models().at(model_n_);
    reform_manager_creator().create_man()->reform_object(model, move, scale, turn);
}

scale_model::scale_model(const double &x,
                       const double &y,
                       const double &z,
                       const size_t &n)
    : kx(x), ky(y), kz(z), model_n_(n) {}

void scale_model::execute()
{
    point move(0, 0, 0);
    point scale(kx, ky, kz);
    point turn(0, 0, 0);

    std::shared_ptr<object> model = scene_manager_creator().create_man()->get_scene()->get_models().at(model_n_);
    reform_manager_creator().create_man()->reform_object(model, move, scale, turn);
}

rotate_model::rotate_model(const double &x,
                         const double &y,
                         const double &z,
                         const size_t &n)
    : ox(x), oy(y), oz(z), model_n_(n) {}

void rotate_model::execute()
{
    point move(0, 0, 0);
    point scale(1, 1, 1);
    point turn(ox, oy, oz);

    std::shared_ptr<object> model = scene_manager_creator().create_man()->get_scene()->get_models().at(model_n_);
    reform_manager_creator().create_man()->reform_object(model, move, scale, turn);
}

reform_model::reform_model(const size_t &n,
                         const point &m,
                         const point &s,
                         const point &t)
    : model_n_(n), move(m), scale(s), turn(t) {}

void reform_model::execute()
{
    std::shared_ptr<object> model = scene_manager_creator().create_man()->get_scene()->get_models().at(model_n_);
    reform_manager_creator().create_man()->reform_object(model, move, scale, turn);
}

load_model::load_model(std::string fname)
    : file_name(std::move(fname)) {}

void load_model::execute()
{
    auto model = load_manager_cretor().create_man()->load_model(file_name);
    scene_manager_creator().create_man()->get_scene()->add_model(model);
}

add_model::add_model(std::shared_ptr<object> model)
    : model(std::move(model)) {}

void add_model::execute()
{
     scene_manager_creator().create_man()->get_scene()->add_model(model);
}

remove_model::remove_model(const size_t &m)
    : model_n_(m) {}

void remove_model::execute()
{
     scene_manager_creator().create_man()->get_scene()->remove_model(model_n_);
}

count_model::count_model(std::shared_ptr<size_t> &count)
    : count_(count) {}

void count_model::execute()
{
    (*count_) = scene_manager_creator().create_man()->get_scene()->get_models().get_size();
}
