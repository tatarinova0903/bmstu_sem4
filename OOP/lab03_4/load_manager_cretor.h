#ifndef LOAD_MANAGER_CRETOR_H
#define LOAD_MANAGER_CRETOR_H

#include "loader.h"

class load_manager_cretor
{
public:
    std::shared_ptr<abstract_loader> create_man();

private:
    std::shared_ptr<abstract_loader> manager_;
    void create_instance();
};

#endif // LOAD_MANAGER_CRETOR_H
