package br.com.utily.ecommerce.entity.domain.product;

import br.com.utily.ecommerce.entity.domain.AlternativeDomainEntity;
import br.com.utily.ecommerce.entity.domain.product.category.Category;
import br.com.utily.ecommerce.entity.domain.product.provider.Provider;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleItem;
import br.com.utily.ecommerce.entity.domain.stock.Stock;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Component
@Table(name = "products")
public class Product extends AlternativeDomainEntity {

    @Basic
    @Column(name = "prt_title")
    private String title;

    @Basic
    @Column(name = "prt_image_url")
    private String imageUrl;

    @Basic
    @Column(name = "prt_price")
    private Double price;

    @Basic
    @Column(name = "prt_operation")
    private String operationMode;

    @Basic
    @Column(name = "prt_characteristics")
    private String characteristics;

    @Basic
    @Column(name = "prt_height")
    private String height;

    @Basic
    @Column(name = "prt_width")
    private String width;

    @Basic
    @Column(name = "prt_weight")
    private String weight;

    @Basic
    @Column(name = "prt_depth")
    private String depth;

    @OneToOne
    @JoinColumn(name = "prt_pvd_id", foreignKey = @ForeignKey(name = "prt_pvd_fk"))
    private Provider provider;

    @OneToOne
    @JoinColumn(name = "prt_ctg_id", foreignKey = @ForeignKey(name = "prt_ctg_fk"))
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<SaleItem> relatedSales = new ArrayList<>();

    @OneToOne(mappedBy = "product")
    private Stock stock;

    public Boolean hasStock() {
        return stock != null;
    }

    public Boolean hasNotStock() {
        return !hasStock();
    }

    public Boolean hasOnStock() {
        return hasStock() && stock.getAmount() > 0;
    }
}
