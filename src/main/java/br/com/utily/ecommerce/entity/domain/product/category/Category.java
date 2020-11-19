package br.com.utily.ecommerce.entity.domain.product.category;

import br.com.utily.ecommerce.entity.domain.AlternativeDomainEntity;
import br.com.utily.ecommerce.entity.domain.product.Product;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Component
@Table(name = "categories")
public class Category extends AlternativeDomainEntity {

    @Basic
    @Column(name = "ctg_name")
    private String name;

    @ManyToMany
    @JoinTable(name = "products_categories",
                joinColumns = @JoinColumn(name = "pct_ctg_id"),
                inverseJoinColumns = @JoinColumn(name = "pct_prt_id"))
    private Collection<Product> products = new HashSet<>();
}
