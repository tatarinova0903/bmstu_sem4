#include "scene.h"

void scene::add_model(std::shared_ptr<object> model)
{
    this->models->add(model);
}

void scene::remove_model(const size_t index)
{
//    this->models->remove(index);
}

void scene::add_camera(const double x, const double y, const double z)
{
    std::shared_ptr<camera> cam(new camera);
    point cp(x, y, z);
    cam->reform(cp, cp, cp);
    this->cams.push_back(cam);
}

void scene::remove_camera(const size_t index)
{
    this->cams.remove(index);
}

vector<std::shared_ptr<object>> scene::get_models()
{
    return this->models->get_objects();
}

std::shared_ptr<composite> scene::get_composite()
{
    return this->models;
}

vector<std::shared_ptr<camera>> scene::get_cams()
{
    return this->cams;
}


