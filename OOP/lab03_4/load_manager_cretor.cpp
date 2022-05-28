#include "load_manager_cretor.h"

void load_manager_cretor::create_instance()
{
    static std::shared_ptr<base_loader> floader(new file_loader);
    auto manager_ = std::shared_ptr<abstract_loader>(new model_loader(floader));
}


std::shared_ptr<abstract_loader> load_manager_cretor::create_man()
{
    if (manager_ == nullptr)
    {
        create_instance();
    }

    return manager_;
}
